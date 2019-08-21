package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.condition.PermissionUpdateCondition;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.BaseResponse;
import com.yueny.rapid.data.resp.pojo.response.JsonListResponse;
import com.yueny.rapid.data.resp.pojo.response.ListResponse;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.date.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单、权限管理
 *
 * @author - langhsu
 * @create - 2018/5/18
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseBizController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表首页
     */
    @RequestMapping("/index.html")
    public String index(ModelMap model, HttpServletRequest request) {
        return "/admin/role/permission/list";
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public ListResponse<PermissionBO> list() {
        ListResponse<PermissionBO> response = new ListResponse<>();

        List<PermissionBO> list = permissionService.findAll();

        response.setData(list);
        return response;
    }

    /**
     * 访问记录详情
     */
    @RequestMapping(value="/view", method = {RequestMethod.GET})
    public String get(Long id, ModelMap model) {
        // 1为修改模式. 0为新增模式, 2为预览模式
        Integer modelType = 0;

        if (id != null && id > 0) {
            PermissionBO permissionBO = permissionService.get(id);
            if(permissionBO != null){
                model.put("permission", permissionBO);
                modelType = 1;
            }
        }

        model.put("modelType", modelType);

        return "/admin/role/permission/view";
    }

    /**
     * 新增和修改权限
     * @param condition 权限更新条件
     */
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse update(PermissionUpdateCondition condition) {
        NormalResponse<Void> response = new NormalResponse<>();

        if (condition != null) {
            if (condition.getId() != null && condition.getId() > 0) {
                PermissionBO permissionBO = permissionService.get(condition.getId());
                if(permissionBO == null){
                    response.setCode(ErrorType.INVALID_ERROR.getCode());
                    response.setMessage("欲更新数据不存在");
                    return response;
                }

                permissionBO.setName(condition.getName());
                permissionBO.setDescription(condition.getDescription());
                permissionBO.setWeight(condition.getWeight());
                permissionBO.setParentId(condition.getParentId());

                permissionService.updateById(permissionBO);
            } else {
                PermissionBO permissionBO = new PermissionBO();
                permissionBO.setName(condition.getName());
                permissionBO.setDescription(condition.getDescription());
                permissionBO.setWeight(condition.getWeight());
                permissionBO.setParentId(condition.getParentId());

                permissionService.insert(permissionBO);
            }
        }

        response.setMessage("数据保存成功！");
        return response;
    }
}
