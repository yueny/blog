package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.UserRoleBo;
import com.mtons.mblog.entity.bao.UserRole;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.service.api.bao.IPlusBizService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author - langhsu on 2018/2/11
 */
public interface UserRoleService extends IPlusBizService<UserRoleBo, UserRole> {
    List<UserRoleBo> findAllByUserId(long userId);

    List<UserRoleBo> findAllByRoleId(long roleId);

    List<UserRoleBo> findAllByUserId(Collection<Long> userId);

    /**
     * 查询用户已有的角色 roleId
     * @param userId 用户ID
     * @return
     */
    List<Long> listRoleIds(long userId);

    /**
     * 修改用户角色
     * @param userId 用户ID
     * @param roleIds 要授权的角色ID
     */
    void updateRole(long userId, String uid, Set<Long> roleIds);


    Map<Long, List<RolePermissionVO>> findMapByUserIds(List<Long> userIds);
}
