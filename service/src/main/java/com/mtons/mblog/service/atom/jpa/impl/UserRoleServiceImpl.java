package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.dao.repository.UserRoleRepository;
import com.mtons.mblog.entity.jpa.UserRole;
import com.mtons.mblog.service.atom.jpa.RoleService;
import com.mtons.mblog.service.atom.jpa.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public List<Long> listRoleIds(long userId) {
        List<UserRole> list = userRoleRepository.findAllByUserId(userId);
        List<Long> roleIds = new ArrayList<>();
        if (null != list) {
            list.forEach(po -> roleIds.add(po.getRoleId()));
        }
        return roleIds;
    }

    @Override
    public List<RoleBO> listRoles(long userId) {
        List<Long> roleIds = listRoleIds(userId);
        return new ArrayList<RoleBO>(roleService.findByIds(new HashSet<>(roleIds)).values());
    }

    @Override
    public Map<Long, List<RoleBO>> findMapByUserIds(List<Long> userIds) {
        List<UserRole> list = userRoleRepository.findAllByUserIdIn(userIds);
        Map<Long, Set<Long>> map = new HashMap<>();

        list.forEach(po -> {
            Set<Long> roleIds = map.computeIfAbsent(po.getUserId(), k -> new HashSet<>());
            roleIds.add(po.getRoleId());
        });

        Map<Long, List<RoleBO>> ret = new HashMap<>();
        map.forEach((k, v) -> {
            ret.put(k, new ArrayList<RoleBO>(roleService.findByIds(v).values()));
        });
        return ret;
    }

    @Override
    @Transactional
    public void updateRole(long userId, String uid, Set<Long> roleIds) {
        // 判断是否清空已授权角色
        if (null == roleIds || roleIds.isEmpty()) {
            userRoleRepository.deleteByUserId(userId);
        } else {
            List<UserRole> list = userRoleRepository.findAllByUserId(userId);
            List<Long> exitIds = new ArrayList<>();

            // 如果已有角色不在 新角色列表中, 执行删除操作
            if (null != list) {
                list.forEach(po -> {
                    if (!roleIds.contains(po.getRoleId())) {
                        userRoleRepository.delete(po);
                    } else {
                        exitIds.add(po.getRoleId());
                    }
                });
            }

            // 保存不在已有角色中的新角色ID
            roleIds.stream().filter(id -> !exitIds.contains(id)).forEach(roleId -> {
                UserRole po = new UserRole();
                po.setUserId(userId);
                po.setRoleId(roleId);
                po.setUid(uid);

                userRoleRepository.save(po);
            });
        }


    }
}
