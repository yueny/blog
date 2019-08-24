package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.RolePermissionBo;
import com.mtons.mblog.dao.mapper.RolePermissionMapper;
import com.mtons.mblog.entity.bao.RolePermission;
import com.mtons.mblog.service.atom.bao.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
@Service
public class RolePermissionServiceImpl extends AbstractPlusService<RolePermissionBo, RolePermission, RolePermissionMapper>
        implements RolePermissionService {

    @Override
    public List<RolePermissionBo> findPermissionsByRoleId(long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new QueryWrapper<RolePermission>().lambda();
        queryWrapper.eq(RolePermission::getRoleId, roleId);

        return findAll(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByRoleId(long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new QueryWrapper<RolePermission>().lambda();
        queryWrapper.eq(RolePermission::getRoleId, roleId);

        delete(queryWrapper);
//        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional
    public boolean add(Set<RolePermissionBo> rolePermissions) {
        List<RolePermission> entrys = map(rolePermissions, RolePermission.class);

        boolean rs = saveOrUpdateBatch(entrys);
        return rs;
//        rolePermissionRepository.saveAll(entrys);
    }
}
