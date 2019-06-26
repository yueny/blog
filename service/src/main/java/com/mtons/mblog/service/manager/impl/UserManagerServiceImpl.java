/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.RoleVO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.UserSecurityEntry;
import com.mtons.mblog.model.UserVO;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.IUserSecurityService;
import com.mtons.mblog.service.atom.ResourceService;
import com.mtons.mblog.service.atom.UserRoleService;
import com.mtons.mblog.service.atom.UserService;
import com.mtons.mblog.service.manager.IUserManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理服务，汇总用户信息、头像信息、权限角色信息和安全基本信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:05
 *
 */
@Service
public class UserManagerServiceImpl extends BaseService implements IUserManagerService {
	@Autowired
    private IUserSecurityService userSecurityService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;

    @Override
    public UserVO get(String uid) {
        UserBO userBO = userService.get(uid);
        if(userBO == null){
            return null;
        }

        // Role
        UserVO userVO = mapAny(userBO, UserVO.class);
        userVO.setRoles(userRoleService.listRoles(userBO.getId()));

        // Security
        LambdaQueryWrapper<UserSecurityEntry> queryWrapper = new QueryWrapper<UserSecurityEntry>().lambda();
        queryWrapper.eq(UserSecurityEntry::getUid, uid);
        UserSecurityEntry entry = userSecurityService.getOne(queryWrapper);
        if(entry != null){
            userVO.setNeedChangePw(entry.getNeedChangePw());
        }

        // Resource
        if(StringUtils.isNotEmpty(userBO.getThumbnailCode())){
            ResourceBO resourceBO = resourceService.findByThumbnailCode(userBO.getThumbnailCode());
            if(resourceBO != null){
                userVO.setThumbnail(resourceBO);
            }
        }

        return userVO;
    }

    @Override
    public Page<UserVO> paging(Pageable pageable, String name) {
        Page<UserBO> page = userService.paging(pageable, name);

        List<Long> userIds = new ArrayList<>();
        page.getContent().forEach(item -> {
            userIds.add(item.getId());
        });
        Map<Long, List<RoleVO>> map = userRoleService.findMapByUserIds(userIds);

        List<UserVO> list = mapAny(page.getContent(), UserVO.class);
        list.forEach(userVo -> {
            // Role
            userVo.setRoles(map.get(userVo.getId()));

            // Security
            LambdaQueryWrapper<UserSecurityEntry> queryWrapper = new QueryWrapper<UserSecurityEntry>().lambda();
            queryWrapper.eq(UserSecurityEntry::getUid, userVo.getUid());
            UserSecurityEntry entry = userSecurityService.getOne(queryWrapper);
            if(entry != null){
                userVo.setNeedChangePw(entry.getNeedChangePw());
            }

            // Resource
            if(StringUtils.isNotEmpty(userVo.getThumbnailCode())){
                ResourceBO resourceBO = resourceService.findByThumbnailCode(userVo.getThumbnailCode());
                if(resourceBO != null){
                    userVo.setThumbnail(resourceBO);
                }
            }
        });

        return new PageImpl<>(list, pageable, page.getTotalElements());
    }
}
