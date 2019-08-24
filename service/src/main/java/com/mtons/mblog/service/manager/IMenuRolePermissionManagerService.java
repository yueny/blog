package com.mtons.mblog.service.manager;

import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.model.MenuVo;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.model.menu.MenuTreeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色-权限管理服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-24 11:03
 */
public interface IMenuRolePermissionManagerService {
    /**
     * 获取指定角色所拥有的的权限列表
     * @param roleId 角色id
     * @return 权限列表
     */
    List<PermissionBO> findAllPermByRoleId(long roleId);

    /**
     * 列出所有菜单项， 树化， 含菜单权限值
     * @return 菜单列表
     */
    List<MenuTreeVo> findAllMenuForTree();

    /**
     * 列出所有菜单项， 带权限信息
     * @return 菜单列表
     */
    List<MenuVo> findAllForPermission();

    /**
     * 分页查询角色
     * @param pageable 分页对象
     * @param roleName 角色名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<RolePermissionVO> findAllByRoleName(Pageable pageable, String roleName);

    /**
     * 根据角色ID获得角色信息， 含角色所用拥有的所有权限信息
     * @param roleId 角色ID
     * @return Role
     */
    RolePermissionVO getForVo(long roleId);

    /**
     * 根据角色id获取角色列表，含每个角色的权限列表
     * @param roleIds
     * @return
     */
    Map<Long, RolePermissionVO> findMapByIds(Set<Long> roleIds);



}
