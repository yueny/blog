package com.mtons.mblog.service.atom;

import com.mtons.mblog.bo.PermissionVO;
import com.mtons.mblog.bo.RolePermissionVO;

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
