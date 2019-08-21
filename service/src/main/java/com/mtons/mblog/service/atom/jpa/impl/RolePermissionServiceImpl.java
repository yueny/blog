package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RolePermissionVO;
import com.mtons.mblog.dao.repository.RolePermissionRepository;
import com.mtons.mblog.entity.jpa.RolePermission;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.atom.jpa.RolePermissionService;
import com.mtons.mblog.service.BaseService;
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
    private PermissionService permissionService;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionBO> findPermissions(long roleId) {
        List<RolePermission> rps = rolePermissionRepository.findAllByRoleId(roleId);

        if (CollectionUtils.isEmpty(rps)) {
            return Collections.<PermissionBO>emptyList();
        }

        Set<Long> pids = new HashSet<>();
        rps.forEach(rp -> pids.add(rp.getPermissionId()));
        return permissionService.findAllById(pids);
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
