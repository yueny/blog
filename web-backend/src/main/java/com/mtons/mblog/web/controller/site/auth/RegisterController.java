/**
 * 
 */
package com.mtons.mblog.web.controller.site.auth;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.consts.StorageConsts;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.modules.service.SecurityCodeService;
import com.mtons.mblog.service.atom.UserService;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class RegisterController extends BaseController {
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private IUserManagerService userManagerService;

	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getProfile();
		if (profile != null) {
			return String.format(Views.REDIRECT_USER_HOME, profile.getDomainHack());
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(UserBO post, HttpServletRequest request, ModelMap model) {
		String view = view(Views.REGISTER);
		try {
			// 注册开启邮箱验证
			if (siteOptions.getControls().isRegister_email_validate()) {
				String code = request.getParameter("code");
				Assert.state(StringUtils.isNotBlank(post.getEmail()), "请输入邮箱地址");
				Assert.state(StringUtils.isNotBlank(code), "请输入邮箱验证码");
				securityCodeService.verify(post.getEmail(), Consts.CODE_REGISTER, code);
			}

			// 是否放开了注册
			if (!siteOptions.getControls().isRegister()) {
				// 其实注册未放开~
				model.put("data", Result.failure("暂未开放，敬请期待~"));
				return view;
			}

			// 默认头像
			post.setAvatar(StorageConsts.AVATAR);
			userManagerService.register(post);

			// 进行认证
			Result<AccountProfile> result = executeLogin(post.getUsername(), post.getPassword(), false);
			view = String.format(Views.REDIRECT_USER_HOME, result.getData().getDomainHack());
		} catch (Exception e) {
            model.addAttribute("post", post);
			model.put("data", Result.failure(e.getMessage()));
		}
		return view;
	}

}