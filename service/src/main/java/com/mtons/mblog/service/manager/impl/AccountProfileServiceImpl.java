package com.mtons.mblog.service.manager.impl;

import com.mtons.mblog.bo.BadgesCount;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.manager.IAccountProfileService;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.yueny.rapid.lang.common.enums.YesNoType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-26 10:02
 */
@Service
public class AccountProfileServiceImpl extends BaseService
        implements IAccountProfileService {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private IUserManagerService userManagerService;

    @Override
    public AccountProfile find(Long userId) {
        UserBO userBO = userService.find(userId);
        Assert.notNull(userBO, "账户不存在");

        // Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
        // po.setLastLogin(Calendar.getInstance().getTime());

        AccountProfile accountProfile = mapAny(userBO, AccountProfile.class);

        accountProfile.setSide(YesNoType.N);
        List<RolePermissionVO> rolePermissionVOS = userManagerService.findListRolesByUserId(userBO.getId());

        Set<String> permissionNames = new HashSet<>();
        for (RolePermissionVO rolePermissionVO: rolePermissionVOS) {
            if(CollectionUtils.isNotEmpty(rolePermissionVO.getPermissions())){
                for (PermissionBO permissionBO: rolePermissionVO.getPermissions()) {
                    permissionNames.add(permissionBO.getName());
                }
            }

            if(rolePermissionVO.getSide() == YesNoType.Y){
                accountProfile.setSide(YesNoType.Y);
            }
        }
        accountProfile.setPermissionNames(Collections.unmodifiableSet(permissionNames));

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(userId));
        accountProfile.setBadgesCount(badgesCount);

        return accountProfile;
    }
}
