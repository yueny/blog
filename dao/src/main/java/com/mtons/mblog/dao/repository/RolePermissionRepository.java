package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {
    int deleteByRoleId(long roleId);

    List<RolePermission> findAllByRoleId(long roleId);


    List<RolePermission> findAllByUid(String uid);
}
