package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.SecurityCodeService;
import com.mtons.mblog.service.comp.base.IUserPassportService;
import com.mtons.mblog.web.controller.BaseBizController;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author langhsu on 2015/8/14.
 */
@Controller
public class ForgotController extends BaseBizController {
    @Autowired
    private UserService userService;
    @Autowired
    private IUserPassportService userPassportService;
    @Autowired
    private SecurityCodeService securityCodeService;

    @GetMapping("/forgot")
    public String view() {
        return view(Views.FORGOT);
    }

    @PostMapping("/forgot")
    public String reset(String email, String code, String password, ModelMap model) {
        String view = view(Views.FORGOT);
        try {
            Assert.hasLength(email, "请输入邮箱地址");
            Assert.hasLength(code, "请输入验证码");

            UserBO user = userService.findByEmail(email);
            Assert.notNull(user, "账户不存在");

            securityCodeService.verify(String.valueOf(user.getId()), Consts.CODE_FORGOT, code);

            // 验证码通过后，直接修改密码
            userPassportService.changePassword(user.getUid(), password);
            model.put("data", Result.successMessage("恭喜您, 密码重置成功"));
            view = view(Views.REGISTER);
        } catch (Exception e) {
            model.put("data", Result.failure(e.getMessage()));
        }
        return view;
    }
}
