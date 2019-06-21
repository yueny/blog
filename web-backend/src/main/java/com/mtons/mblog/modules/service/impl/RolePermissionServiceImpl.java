package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.data.PermissionVO;
import com.mtons.mblog.modules.data.RolePermissionVO;
import com.mtons.mblog.modules.repository.PermissionRepository;
import com.mtons.mblog.modules.repository.RolePermissionRepository;
import com.mtons.mblog.entity.Permission;
import com.mtons.mblog.entity.RolePermission;
import com.mtons.mblog.modules.service.RolePermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
@Service
public class RolePermissionServiceImpl extends BaseService implements RolePermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionVO> findPermissions(long roleId) {
        List<RolePermission> rps = rolePermissionRepository.findAllByRoleId(roleId);

        if (CollectionUtils.isEmpty(rps)) {
            return Collections.<PermissionVO>emptyList();
        }

        Set<Long> pids = new HashSet<>();
        rps.forEach(rp -> pids.add(rp.getPermissionId()));
        List<Permission> entryList = permissionRepository.findAllById(pids);

        return map(entryList, PermissionVO.class);
    }

    @Override
    @Transactional
    public void deleteByRoleId(long roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional
    public void add(Set<RolePermissionVO> rolePermissions) {
        List<RolePermission> entrys = map(rolePermissions, RolePermission.class);

        rolePermissionRepository.saveAll(entrys);
    }
}
