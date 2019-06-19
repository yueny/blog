package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.PermissionVO;
import com.mtons.mblog.modules.data.RolePermissionVO;
import com.mtons.mblog.modules.entity.Permission;
import com.mtons.mblog.modules.entity.RolePermission;

import java.util.List;
import java.util.Set;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
public interface RolePermissionService {
    List<PermissionVO> findPermissions(long roleId);
    void deleteByRoleId(long roleId);
    void add(Set<RolePermissionVO> rolePermissions);

}
