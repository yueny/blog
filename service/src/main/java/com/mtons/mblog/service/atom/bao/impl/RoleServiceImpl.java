package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RolePermissionBo;
import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.bo.UserRoleBo;
import com.mtons.mblog.dao.mapper.RoleMapper;
import com.mtons.mblog.entity.bao.Role;
import com.mtons.mblog.entity.bao.RolePermission;
import com.mtons.mblog.service.atom.bao.RoleService;
import com.mtons.mblog.service.atom.bao.RolePermissionService;
import com.mtons.mblog.service.atom.bao.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractPlusService<RoleBO, Role, RoleMapper>
        implements RoleService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Page<RoleBO> findAll(Pageable pageable, String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(roleName)){
            queryWrapper.like("name", roleName);
        }
        queryWrapper.orderByDesc("id");

        return findAll(pageable, queryWrapper);
    }

    @Override
    public List<RoleBO> findByActivate() {
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().lambda();
        queryWrapper.eq(Role::getStatus, StatusType.NORMAL.getValue());

        return findAll(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleBO r, Set<PermissionBO> permissions) {
        RoleBO roleBO = get(r.getId());
        if(roleBO == null){
            roleBO = new RoleBO();
        }

        roleBO.setName(r.getName());
        roleBO.setDescription(r.getDescription());
        roleBO.setStatus(r.getStatus());
        roleBO.setSide(r.getSide());

        saveOrUpdate(roleBO);

        rolePermissionService.deleteByRoleId(roleBO.getId());

        if (permissions != null && permissions.size() > 0) {
            List<RolePermission> rps = new ArrayList<>();
            long roleId = roleBO.getId();
            permissions.forEach(p -> {
                RolePermission rp = new RolePermission();
                rp.setRoleId(roleId);
                rp.setPermissionId(p.getId());
//                rp.setUid();
                rps.add(rp);
            });

            List<RolePermissionBo> list = map(rps, RolePermissionBo.class);
            Set<RolePermissionBo> sets = Sets.newHashSet();
            sets.addAll(list);

            rolePermissionService.add(sets);
        }
    }

    /**
     * 删除角色，已被授权的角色不允许删除
     * @param id 角色ID
     * @return true/false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        List<UserRoleBo> urs = userRoleService.findAllByRoleId(id);
        Assert.state(urs == null || urs.size() == 0, "该角色已经被使用,不能被删除");

        super.delete(id);

        rolePermissionService.deleteByRoleId(id);
        return true;
    }

    @Override
    public void activate(long id, boolean active) {
        RoleBO roleBO = get(id);
        roleBO.setStatus(active ? StatusType.NORMAL : StatusType.CLOSED);

        updateById(roleBO);
    }

}
