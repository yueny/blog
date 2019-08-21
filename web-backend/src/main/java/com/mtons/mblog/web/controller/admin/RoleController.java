/**
 * 
 */
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.entity.jpa.Role;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.atom.jpa.RoleService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/admin/role")
public class RoleController extends BaseBizController {
	@Autowired
    private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 管理表 shiro_role
	 *
	 * @param name
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String paging(String name, ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<RoleBO> page = roleService.paging(pageable, name);
		model.put("name", name);
		model.put("page", page);
		return "/admin/role/list";
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		try{
			if (id != null && id > 0) {
				RoleBO role = roleService.get(id);
				model.put("view", role);
			}

			// 菜单列表
			model.put("permissions", permissionService.findAllForTree());
		}catch (Exception e){
			e.printStackTrace();
		}

        return "/admin/role/view";
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
        
        if (Role.ADMIN_ID == role.getId()) {
			data = Result.failure("管理员角色不可编辑");
        } else {
            roleService.update(role, permissions);
            data = Result.success();
        }
        model.put("data", data);
        return "redirect:/admin/role/list";
	}
	
	@RequestMapping("/activate")
	@ResponseBody
	public Result activate(Long id, Boolean active) {
		Result ret = Result.failure("操作失败");
		if (id != null && id != Role.ADMIN_ID) {
			roleService.activate(id, active);
			ret = Result.success();
		}
		return ret;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@RequestParam("id") Long id) {
		Result ret;
		if (Role.ADMIN_ID == id) {
			ret = Result.failure("管理员不能操作");
        }else if(roleService.delete(id)){
        	ret = Result.success();
        }else{
        	ret = Result.failure("该角色不能被操作");
        }
		return ret;
	}
}
