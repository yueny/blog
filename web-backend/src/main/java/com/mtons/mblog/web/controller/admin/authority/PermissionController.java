package com.mtons.mblog.web.controller.admin.authority;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.enums.FuncType;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.condition.PermissionUpdateCondition;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.BaseResponse;
import com.yueny.rapid.data.resp.pojo.response.ListResponse;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 菜单、权限管理
 *
 * @author - langhsu
 * @create - 2018/5/18
 */
@Controller
@RequestMapping("/admin/authority/permission")
public class PermissionController extends BaseBizController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表首页
     */
    @RequestMapping("/index.html")
    public String index(ModelMap model, HttpServletRequest request) {
        model.put("title", "资源权限管理");

        return "/admin/authority/permission/list";
    }

    /**
     * 获取权限列表， 非树结构。树化在前端页面完成
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
     * 详情
     */
    @RequestMapping(value="/view", method = {RequestMethod.GET})
    public String get(Long id, ModelMap model) {
        if (id != null && id > 0) {
            PermissionBO permissionBO = permissionService.find(id);
            if(permissionBO != null){
                model.put("permission", permissionBO);
            }
        }

        // 选择资源文件的父级， 所以为菜单
        // 非树结构
//        List<PermissionBO> permissionRootList = permissionService.findAllByParentId();
        // 树结构
        List<PermissionTreeVo> permissionRootList = permissionService.findAllForTree(FuncType.MENU);
        model.put("permissionList", permissionRootList);
        model.put("funcTypeList", FuncType.values());

        return "/admin/authority/permission/view";
    }

    /**
     * 新增和修改权限
     * @param condition 权限更新条件
     */
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse update(PermissionUpdateCondition condition) {
        BaseResponse response = new BaseResponse();

        if (condition != null) {
            PermissionBO permissionBO;
            if (condition.getId() != null && condition.getId() > 0) {
                permissionBO = permissionService.find(condition.getId());
                if(permissionBO == null){
                    response.setCode(ErrorType.INVALID_ERROR.getCode());
                    response.setMessage("欲更新数据不存在");
                    return response;
                }

                permissionBO.setName(condition.getName());
                permissionBO.setDescription(condition.getDescription());
                permissionBO.setWeight(condition.getWeight());
                permissionBO.setParentId(condition.getParentId());
                permissionBO.setFuncType(condition.getFuncType());
            } else {
                permissionBO = new PermissionBO();
                permissionBO.setName(condition.getName());
                permissionBO.setDescription(condition.getDescription());
                permissionBO.setWeight(condition.getWeight());
                permissionBO.setParentId(condition.getParentId());
                permissionBO.setFuncType(condition.getFuncType());
            }

            permissionService.saveOrUpdate(permissionBO);
        }

        response.setMessage("数据保存成功！");
        return response;
    }

    /**
     * 批量删除
     * @param ids 数据主键列表
     * @return 删除结果
     */
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    public NormalResponse<Boolean> delete(@RequestParam("id") List<Long> ids) {
        NormalResponse<Boolean> resp = new NormalResponse<>();

        if (CollectionUtils.isNotEmpty(ids)) {
            try {
                Set<Long> idList = Sets.newHashSet();
                idList.addAll(ids);

                permissionService.deleteByIds(idList);
                resp.setData(true);
            } catch (MtonsException e) {
                resp.setCode(e.getCode());
                resp.setMessage("删除异常：" + e.getErrorMessage());
                resp.setData(false);

                logger.error("exception:", e);
            } catch (Exception e) {
                resp.setCode(ErrorType.INVALID_ERROR.getCode());
                resp.setMessage("删除异常：" + e.getMessage());
                resp.setData(false);

                logger.error("exception:", e);
            }
        }
        return resp;
    }
}
