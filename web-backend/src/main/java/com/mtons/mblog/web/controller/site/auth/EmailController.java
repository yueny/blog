package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.service.comp.ICacheService;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.consts.OptionsKeysConsts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.modules.comp.MailService;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.service.atom.UserService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author langhsu on 2015/8/14.
 */
@RestController
@RequestMapping("/email")
public class EmailController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SecurityCodeService securityCodeService;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    private static final String EMAIL_TITLE = "[{0}]您正在使用邮箱安全验证服务";

    /**
     * 5分钟内最多发送验证码3次
     */
    @GetMapping("/send_code")
    public Result sendCode(String email, @RequestParam(name = "type", defaultValue = "1") Integer type) {
        Assert.hasLength(email, "请输入邮箱地址");
        Assert.notNull(type, "缺少必要的参数");

        // 如果键不存在则新增,存在则不改变已经有的值。 或者使用 hasKey
        boolean isHaving = stringRedisTemplate.opsForValue().setIfAbsent(ICacheService.FREQUENCE_BLOCK_UNIT_KEY, "0", 5, TimeUnit.MINUTES);
        if(!isHaving){ // 已经存在，新增失败
            int count = Integer.valueOf(stringRedisTemplate.opsForValue().get(ICacheService.FREQUENCE_BLOCK_UNIT_KEY));
            if(count > 3){
                return Result.failure("请求过于频繁，请5分钟后再试~");
            }
        }

        //val +1
        stringRedisTemplate.boundValueOps(ICacheService.FREQUENCE_BLOCK_UNIT_KEY).increment(1L);

        String key = email;

        switch (type) {
            // 绑定邮箱
            case Consts.CODE_BIND:
                AccountProfile profile = getProfile();
                Assert.notNull(profile, "请先登录后再进行此操作");
                key = String.valueOf(profile.getId());
                break;
            // 找回密码
            case Consts.CODE_FORGOT:
                UserBO user = userService.getByEmail(email);
                Assert.notNull(user, "账户不存在");
                key = String.valueOf(user.getId());
                break;
            // 注册
            case Consts.CODE_REGISTER:
                key = email;
                break;
        }

        // 发送间隔时间不能少于1分钟
        String code = securityCodeService.generateCode(key, type, email);
        Map<String, Object> context = new HashMap<>();
        context.put("code", code);

        String title = MessageFormat.format(EMAIL_TITLE, siteOptions.getValue(OptionsKeysConsts.SITE_NAME));
        mailService.sendTemplateEmail(email, title, Consts.EMAIL_TEMPLATE_CODE, context);
        return Result.successMessage("邮件已发送， 如未收到邮件，请联系管理员!");
    }

}
