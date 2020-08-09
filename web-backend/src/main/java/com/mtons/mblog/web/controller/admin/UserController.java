/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.vo.UserVO;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.bao.RoleService;
import com.mtons.mblog.service.atom.bao.UserRoleService;
import com.mtons.mblog.service.internal.IUserPassportService;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.mtons.mblog.service.util.PKUtil;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author langhsu
 *
 */
@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseBizController {
	@Autowired
	private IUserPassportService userPassportService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private IUserManagerService userManagerService;
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 用户列表页面
	 */
	@RequestMapping("/list")
	public String list(String name, ModelMap model) {
		Page<UserVO> page = userManagerService.paging(wrapPageable(), name);

		model.put("name", name);
		model.put("page", page);
		return "/admin/user/list";
	}

	/**
	 * 修改角色页面
	 */
	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		if (PKUtil.available(id)) {
			UserBO bo = userService.find(id);

			UserVO userVO = userManagerService.find(bo.getUid());

			model.put("view", userVO);
		}

		// 角色列表 admin/user/guest 等
		model.put("roles", roleService.findByActivate());
		return "/admin/user/view";
	}

	/**
	 * 修改角色 动作
	 */
	@PostMapping("/update_role")
//	@RequiresPermissions("user:role")
	public String postAuthc(Long id, String uid, @RequestParam(value = "roleIds", required=false) Set<Long> roleIds, ModelMap model) {
		if (PKUtil.available(id)) {
			// 由于用户已经存在，所以更多的为变更角色关系
			userRoleService.updateRole(id, uid, roleIds);
		}else{
//			UserRoleBo userRoleBo = new UserRoleBo();
//			userRoleService.saveOrUpdate(userRoleBo);
		}

		model.put("data", Result.success());
		return "redirect:/admin/user/list";
	}

	/**
	 * 密码修改页面
	 */
	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
//	@RequiresPermissions("user:pwd")
	public String pwsView(Long id, ModelMap model) {
		UserBO ret = userService.find(id);
		model.put("view", ret);
		return "/admin/user/pwd";
	}

	/**
	 * 密码修改 动作
	 */
	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
//	@RequiresPermissions("user:pwd")
	public String pwd(Long id, String newPassword, ModelMap model) {
		UserBO ret = userService.find(id);
		model.put("view", ret);

		try {
			userPassportService.changePassword(ret.getUid(), newPassword);
			model.put("message", "修改成功， 新密码 " + newPassword + "， 请牢记！");
		} catch (IllegalArgumentException | InvalidException e) {
			model.put("message", e.getMessage());
			logger.error("exception:", e);
		}
		return "/admin/user/pwd";
	}

	/**
	 * 激活用户 动作
	 */
	@RequestMapping("/open")
//	@RequiresPermissions("user:open")
	@ResponseBody
	public Result open(Long id) {
		UserBO userBO = userService.find(id);
		userService.updateStatus(userBO.getUid(), StatusType.NORMAL);
		return Result.success();
	}

	/**
	 * 禁用用户 动作
	 */
	@RequestMapping("/close")
//	@RequiresPermissions("user:close")
	@ResponseBody
	public Result close(Long id) {
		UserBO userBO = userService.find(id);
		userService.updateStatus(userBO.getUid(), StatusType.CLOSED);
		return Result.success();
	}

//	/**
//	 * 新增用户 动作
//	 */
//	@RequestMapping(value = "/add-user.json", method = { RequestMethod.POST })
//	@ResponseBody
//	public String addUser(HttpServletResponse response) {
//		//.
//
//		return view(Views.USER_ADD_VIEW);
//	}

}
