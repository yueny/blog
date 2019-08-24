package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.UserRoleBo;
import com.mtons.mblog.dao.mapper.UserRoleMapper;
import com.mtons.mblog.entity.bao.UserRole;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.service.atom.bao.UserRoleService;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl extends AbstractPlusService<UserRoleBo, UserRole, UserRoleMapper>
        implements UserRoleService {
    @Autowired
    private IMenuRolePermissionManagerService menuRolePermissionManagerService;

    @Override
    public List<Long> listRoleIds(long userId) {
        List<UserRoleBo> list = findAllByUserId(userId);
        List<Long> roleIds = new ArrayList<>();
        if (null != list) {
            list.forEach(po -> roleIds.add(po.getRoleId()));
        }
        return roleIds;
    }

    @Override
    public List<UserRoleBo> findAllByUserId(long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>().lambda();
        queryWrapper.eq(UserRole::getUserId, userId);

        return findAll(queryWrapper);
    }

    @Override
    public List<UserRoleBo> findAllByRoleId(long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>().lambda();
        queryWrapper.eq(UserRole::getRoleId, roleId);

        return findAll(queryWrapper);
    }

    @Override
    public List<UserRoleBo> findAllByUserId(Collection<Long> userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>().lambda();
        queryWrapper.in(UserRole::getUserId, userId);

        return findAll(queryWrapper);
    }

    @Override
    @Transactional
    public void updateRole(long userId, String uid, Set<Long> roleIds) {
        // 判断是否清空已授权角色
        if (null == roleIds || roleIds.isEmpty()) {
            LambdaQueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>().lambda();
            queryWrapper.eq(UserRole::getUserId, userId);

            delete(queryWrapper);
        } else {
            List<UserRoleBo> list = findAllByUserId(userId);
            List<Long> exitIds = new ArrayList<>();

            // 如果已有角色不在 新角色列表中, 执行删除操作
            if (null != list) {
                list.forEach(po -> {
                    if (!roleIds.contains(po.getRoleId())) {
                        delete(po.getId());
                    } else {
                        exitIds.add(po.getRoleId());
                    }
                });
            }

            // 保存不在已有角色中的新角色ID
            roleIds.stream().filter(id -> !exitIds.contains(id)).forEach(roleId -> {
                UserRoleBo po = new UserRoleBo();
                po.setUserId(userId);
                po.setRoleId(roleId);
                po.setUid(uid);

                saveOrUpdate(po);
            });
        }
    }

    @Override
    public Map<Long, List<RolePermissionVO>> findMapByUserIds(List<Long> userIds) {
        List<UserRoleBo> list = findAllByUserId(userIds);
        Map<Long, Set<Long>> map = new HashMap<>();

        list.forEach(po -> {
            Set<Long> roleIds = map.computeIfAbsent(po.getUserId(), k -> new HashSet<>());
            roleIds.add(po.getRoleId());
        });

        Map<Long, List<RolePermissionVO>> ret = new HashMap<>();
        map.forEach((k, v) -> {
            ret.put(k, new ArrayList<>(menuRolePermissionManagerService.findMapByIds(v).values()));
        });
        return ret;
    }
}
