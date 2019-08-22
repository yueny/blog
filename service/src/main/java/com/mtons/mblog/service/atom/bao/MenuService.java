package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.entity.bao.Menu;
import com.mtons.mblog.model.MenuVo;
import com.mtons.mblog.model.menu.MenuTreeVo;
import com.mtons.mblog.service.api.bao.IPlusBizService;

import java.util.List;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 17:12
 */
public interface MenuService extends IPlusBizService<MenuBo, Menu> {

    /**
     * 查询顶级菜单列表， 父菜单主键 为0
     */
    List<MenuBo> findAllByParentId();

    /**
     * 查询子菜单列表
     * @param parentId 父菜单主键
     */
    List<MenuBo> findAllByParentId(Long parentId);

    /**
     * 列出所有菜单项， 树化
     * @return 菜单列表
     */
    List<MenuTreeVo> findAllForTree();

    /**
     * 列出所有菜单项， 带权限信息
     * @return 菜单列表
     */
    List<MenuVo> findAllForPermission();

    /**
     * 查询引用权限的菜单项列表
     */
    List<MenuBo> findByPermissionId(Long permissionId);
}
