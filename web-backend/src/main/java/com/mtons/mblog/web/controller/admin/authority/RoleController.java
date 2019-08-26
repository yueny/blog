/**
 * 
 */
package com.mtons.mblog.web.controller.admin.authority;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.atom.bao.RoleService;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import com.mtons.mblog.service.util.PageHelper;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.PageListResponse;
import com.yueny.rapid.lang.common.enums.YesNoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;

/**
 * 权限、角色管理
 *
 * @author - langhsu on 2018/2/11
 */
@Controller
@RequestMapping("/admin/authority/role")
public class RoleController extends BaseBizController {
	@Autowired
    private RoleService roleService;
	@Autowired
	private IMenuRolePermissionManagerService menuRolePermissionManagerService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 管理表 shiro_role
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/index.html")
	public String index(ModelMap model) {
		model.put("title", "角色管理");
		return "/admin/authority/role/list";
	}

	/**
	 * 获取角色列表
	 *
	 * @return
	 */
	@RequestMapping("/list.json")
	@ResponseBody
	public PageListResponse<RolePermissionVO> paging(String name) {
		PageListResponse<RolePermissionVO> resp = new PageListResponse<>();
		try{
			// 查询分页结果
			Pageable pageable = wrapPageable();
			Page<RolePermissionVO> page = menuRolePermissionManagerService.findAllByRoleName(pageable, name);

			resp.setList(PageHelper.fromSpringToYuenyPage(page));
		}catch (Exception e){
			resp.setCode(ErrorType.INVALID_ERROR.getCode());

			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), e.getMessage());
			resp.setMessage(msg);

			logger.error("exception: ", e);
		}

		return resp;
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		try{
			if (id != null && id > 0) {;
				model.put("view", menuRolePermissionManagerService.getForVo(id));
			}

			// 分配菜单和权限资源，所以此处为所有权限， 包括菜单和功能
			model.put("permissions", permissionService.findAllForTree(null));// 菜单列表
      		model.put("statusList", StatusType.values());
			model.put("yesNoList", YesNoType.values());
		}catch (Exception e){
			e.printStackTrace();
		}

        return "/admin/authority/role/view";
	}
	
	@RequestMapping("/update")
	public String update(RoleBO role, @RequestParam(value = "perms", required=false) List<Long> perms, ModelMap model) {
		Result data;

		HashSet<PermissionBO> permissions = new HashSet<>();
		if(perms != null && perms.size() > 0){
            for(Long pid: perms){
				PermissionBO p = new PermissionBO();
                p.setId(pid);
				permissions.add(p);
            }
        }

		// TODO 管理员编辑
//        if (Consts.ADMIN_ID == role.getId()) {
//			data = Result.failure("管理员角色不可编辑");
//        } else {
            roleService.update(role, permissions);
            data = Result.success();
//        }
        model.put("data", data);
        return "redirect:/admin/authority/role/index.html";
	}

	/**
	 * 激活或冻结
	 * @param id
	 * @param active true 为激活，false为冻结
	 * @return
	 */
	@RequestMapping("/activate")
	@ResponseBody
	public Result activate(Long id, Boolean active) {
		Result ret = Result.failure("操作失败");
		if (id != null && id != Consts.ADMIN_ID) {
			roleService.activate(id, active);
			ret = Result.success();
		}
		return ret;
	}
	
	@RequestMapping("/delete.json")
	@ResponseBody
	public Result delete(@RequestParam("id") Long id) {
		if(id == null){
			return Result.failure("删除主键不能为空！");
		}

		Result ret;
		if (Consts.ADMIN_ID == id) {
			ret = Result.failure("管理员不能操作");
        }else if(roleService.delete(id)){
        	ret = Result.success();
        }else{
        	ret = Result.failure("该角色不能被操作");
        }
		return ret;
	}
}
