package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.entity.bao.Menu;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;

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
     * 查询引用权限的菜单项列表
     */
    List<MenuBo> findByPermissionId(Long permissionId);
}
