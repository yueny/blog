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
import com.mtons.mblog.base.consts.EntityStatus;
import com.mtons.mblog.base.enums.NeedChangeType;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.bo.UserSecurityBO;
import com.mtons.mblog.entity.bao.UserSecurityEntry;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.model.UserVO;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.IUserSecurityService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.bao.UserRoleService;
import com.mtons.mblog.service.atom.jpa.UserJpaService;
import com.mtons.mblog.service.internal.IPasswdService;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.mtons.mblog.service.seq.SeqType;
import com.mtons.mblog.service.seq.container.ISeqContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

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
    private UserJpaService userJpaService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private IPasswdService passwdService;
    @Autowired
    private ISeqContainer seqContainer;
    @Autowired
    private IMenuRolePermissionManagerService menuRolePermissionManagerService;

    @Override
    public UserVO find(String uid) {
        UserBO userBO = userService.find(uid);
        if(userBO == null){
            return null;
        }

        // Role
        UserVO userVO = mapAny(userBO, UserVO.class);
        userVO.setRoles(findListRolesByUserId(userBO.getId()));

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
        Page<UserBO> page = userJpaService.paging(pageable, name);

        List<Long> userIds = new ArrayList<>();
        page.getContent().forEach(item -> {
            userIds.add(item.getId());
        });
        Map<Long, List<RolePermissionVO>> map = userRoleService.findMapByUserIds(userIds);

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

    @Override
    @Transactional
    public UserVO register(UserBO userBo) {
        Assert.notNull(userBo, "Parameter user can not be null!");
        Assert.hasLength(userBo.getUsername(), "用户名不能为空!");
        Assert.hasLength(userBo.getPassword(), "密码不能为空!");
        Assert.isNull(userService.findByUsername(userBo.getUsername()), "用户名已经存在!");

        // 拷贝一份新对象，不破坏请求数据
        UserBO user = mapAny(userBo, UserBO.class);
        if (StringUtils.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }

        // 密码加密方式
        String salt = passwdService.getSalt();
        String pw = passwdService.encode(user.getPassword(), salt);
        user.setPassword(pw);
        user.setStatus(EntityStatus.ENABLED);

        String uid = seqContainer.getStrategy(SeqType.USER_U_ID).get("");
        user.setUid(uid);
        // 默认
        user.setDomainHack(seqContainer.getStrategy(SeqType.SIMPLE).get(""));
        userService.register(user);

        UserSecurityBO us = new UserSecurityBO();
        us.setNeedChangePw(NeedChangeType.NO);
        us.setSalt(salt);
        us.setUid(uid);
        if(!userSecurityService.save(us)){
            // exception
        };

        return find(uid);
    }

    @Override
    public String tryLogin(String username, String tryPassword) {
        if (StringUtils.isAnyBlank(username, tryPassword)) {
            return "";
        }

        UserBO userBO = userService.findByUsername(username);
        if(userBO == null){
            return "";
        }

        UserSecurityBO us = userSecurityService.getByUid(userBO.getUid());
        if(us != null){
            return passwdService.encode(tryPassword, us.getSalt());
        }

        return passwdService.encode(tryPassword, "");
    }

    @Override
    public List<RolePermissionVO> findListRolesByUserId(Long userId) {
        List<Long> roleIds = userRoleService.listRoleIds(userId);

        return new ArrayList<>(menuRolePermissionManagerService.findMapByIds(new HashSet<>(roleIds)).values());
    }

}
