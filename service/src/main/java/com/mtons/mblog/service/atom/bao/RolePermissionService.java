package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.RolePermissionBo;
import com.mtons.mblog.entity.bao.RolePermission;
import com.mtons.mblog.service.api.bao.IPlusBizService;

import java.util.List;
import java.util.Set;

/**
 * 角色-权限关系
 *
 * @author - langhsu
 * @create - 2018/5/18
 */
public interface RolePermissionService extends IPlusBizService<RolePermissionBo, RolePermission> {

    /**
     * 获取指定角色所拥有的的权限关系对象列表
     * @param roleId 角色id
     * @return 权限列表
     */
    List<RolePermissionBo> findPermissionsByRoleId(long roleId);

    /**
     * 删除指定角色的角色权限关系
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(long roleId);

    /**
     * 新增角色权限关系
     *
     * @param rolePermissions 角色-权限关系对象
     */
    boolean add(Set<RolePermissionBo> rolePermissions);

}
