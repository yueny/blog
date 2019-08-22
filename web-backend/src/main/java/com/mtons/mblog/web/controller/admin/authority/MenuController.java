package com.mtons.mblog.web.controller.admin.authority;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.condition.MenuUpdateCondition;
import com.mtons.mblog.model.MenuVo;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.service.atom.bao.MenuService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 16:08
 */
@Controller
@RequestMapping("/admin/authority/menu")
public class MenuController extends BaseBizController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 列表首页
     */
    @RequestMapping("/index.html")
    public String index(ModelMap model, HttpServletRequest request) {
        model.put("title", "菜单管理");

        return "/admin/authority/menu/list";
    }

    /**
     * 获取菜单列表， 非树结构。树化在前端页面完成
     *
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public ListResponse<MenuVo> list() {
        ListResponse<MenuVo> response = new ListResponse<>();

        List<MenuVo> list = menuService.findAllForPermission();
        response.setData(list);
        return response;
    }

    /**
     * 详情
     */
    @RequestMapping(value="/view", method = {RequestMethod.GET})
    public String get(Long id, ModelMap model) {
        if (id != null && id > 0) {
            MenuBo menuBo = menuService.get(id);
            if(menuBo != null){
                model.put("menuBo", menuBo);
            }
        }

        model.put("menuRootList", menuService.findAllByParentId());

        List<PermissionBO> permissionTree = permissionService.findAll();
//        List<PermissionTreeVo> permissionTree = permissionService.findAllForTree();
        model.put("permissionTree", permissionTree);

        return "/admin/authority/menu/view";
    }

    /**
     * 新增和修改权限
     * @param condition 权限更新条件
     */
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse update(MenuUpdateCondition condition) {
        BaseResponse response = new BaseResponse();

        if (condition != null) {
            if (condition.getId() != null && condition.getId() > 0) {
                MenuBo menuBo = menuService.get(condition.getId());
                if(menuBo == null){
                    response.setCode(ErrorType.INVALID_ERROR.getCode());
                    response.setMessage("欲更新数据不存在");
                    return response;
                }

                menuBo.setName(condition.getName());
                menuBo.setIcon(condition.getIcon());
                menuBo.setUrl(condition.getUrl());
                menuBo.setPermissionId(condition.getPermissionId());
                menuBo.setParentId(condition.getParentId());

                menuService.updateById(menuBo);
            } else {
                MenuBo menuBo = new MenuBo();
                menuBo.setName(condition.getName());
                menuBo.setIcon(condition.getIcon());
                menuBo.setUrl(condition.getUrl());
                menuBo.setPermissionId(condition.getPermissionId());
                menuBo.setParentId(condition.getParentId());

                menuService.insert(menuBo);
            }
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

                menuService.deleteByIds(idList);
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
