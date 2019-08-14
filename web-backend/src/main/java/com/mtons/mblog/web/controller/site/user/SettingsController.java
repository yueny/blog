package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.base.utils.FilePathUtils;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.comp.base.IUserPassportService;
import com.mtons.mblog.service.util.ImageUtils;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.service.atom.jpa.SecurityCodeService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import com.mtons.mblog.web.controller.site.posts.UploadController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : landy
 * @version : 1.0
 */
@Controller
@RequestMapping("/settings")
public class SettingsController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityCodeService securityCodeService;
    @Autowired
    private IUserPassportService userPassportService;

    /**
     * 查看个人基本信息
     */
    @GetMapping(value = "/profile")
    public String view(ModelMap model) {
        AccountProfile profile = getProfile();
        UserBO view = userService.get(profile.getId());
        model.put("view", view);
        return view(Views.SETTINGS_PROFILE);
    }

    @GetMapping(value = "/email")
    public String email() {
        return view(Views.SETTINGS_EMAIL);
    }

    @GetMapping(value = "/avatar")
    public String avatar() {
        return view(Views.SETTINGS_AVATAR);
    }

    @GetMapping(value = "/password")
    public String password() {
        return view(Views.SETTINGS_PASSWORD);
    }

    /**
     * 个人基本信息提交
     * @param name 昵称
     * @param signature 个性签名
     * @param domainHack 个性域名, 如 https://muzinuo.com/{domainHack}
     * @param model
     * @return
     */
    @PostMapping(value = "/profile")
    public String updateProfile(String name, String signature, String domainHack, ModelMap model) {
        Result data;
        AccountProfile profile = getProfile();

        try {
            UserBO user = new UserBO();
            user.setId(profile.getId());
            user.setName(name);
            user.setSignature(signature);
            if(StringUtils.isNotEmpty(domainHack)){
                // TODO 判断 domainHack 是否已经被定义
                //.
                user.setDomainHack(domainHack);
            }

            putProfile(userService.update(user));

            // put 最新信息
            UserBO view = userService.get(profile.getId());
            model.put("view", view);

            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
            logger.error("exception:", e);
        }
        model.put("data", data);
        return view(Views.SETTINGS_PROFILE);
    }

    /**
     *  修改邮箱
     */
    @PostMapping(value = "/email")
    public String updateEmail(String email, String code, ModelMap model) {
        Result data;
        AccountProfile profile = getProfile();
        try {
            Assert.hasLength(email, "请输入邮箱地址");
            Assert.hasLength(code, "请输入验证码");

            securityCodeService.verify(String.valueOf(profile.getId()), Consts.CODE_BIND, code);
            // 先执行修改，内部判断邮箱是否更改，或邮箱是否被人使用
            AccountProfile p = userService.updateEmail(profile.getId(), email);
            putProfile(p);

            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
            logger.error("exception:", e);
        }
        model.put("data", data);
        return view(Views.SETTINGS_EMAIL);
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    public String updatePassword(String oldPassword, String password, ModelMap model) {
        Result data;
        try {
            AccountProfile profile = getProfile();
            userPassportService.modifyPassPort(profile.getUid(), oldPassword, password);

            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
            logger.error("exception:", e);
        }
        model.put("data", data);
        return view(Views.SETTINGS_PASSWORD);
    }

    /**
     *  修改头像
     */
    @PostMapping("/avatar")
    @ResponseBody
    public UploadController.UploadResult updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        UploadController.UploadResult result = new UploadController.UploadResult();
        AccountProfile profile = getProfile();

        // 检查空
        if (null == file || file.isEmpty()) {
            return result.error(UploadController.errorInfo.get("NOFILE"));
        }

        String fileName = file.getOriginalFilename();

        // 检查类型
        if (!FileKit.checkFileType(fileName)) {
            return result.error(UploadController.errorInfo.get("TYPE"));
        }

        // 保存图片
        try {
            String ava100 = FilePathUtils.getUAvatar(profile.getUid(), 240);

            byte[] bytes = ImageUtils.screenshot(file, 240, 240);
            String path = storageFactory.get().writeToStore(bytes, ava100);
//            String path = storageFactory.get().storeScale(file,
//                    NailPathData.builder().nailType(NailType.avatar)
//                            .placeVal(String.valueOf(profile.getId()))
//                            .nailTypeAppend(getAvaPath(profile.getId(), 240))
//                    .build(), 240, 240);

            AccountProfile user = userService.updateAvatar(profile.getId(), path);
            putProfile(user);

            result.ok(UploadController.errorInfo.get("SUCCESS"));
            result.setName(fileName);
            result.setPath(path);
            result.setSize(file.getSize());
        } catch (Exception e) {
            result.error(UploadController.errorInfo.get("UNKNOWN"));
            logger.error("exception:", e);
        }
        return result;
    }
}
