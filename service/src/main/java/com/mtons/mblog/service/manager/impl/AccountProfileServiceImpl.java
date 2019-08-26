package com.mtons.mblog.service.manager.impl;

import com.mtons.mblog.bo.BadgesCount;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.manager.IAccountProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    @Override
    public AccountProfile get(Long userId) {
        UserBO userBO = userService.get(userId);
        Assert.notNull(userBO, "账户不存在");

        // Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
        // po.setLastLogin(Calendar.getInstance().getTime());

        AccountProfile accountProfile = mapAny(userBO, AccountProfile.class);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(userId));
        accountProfile.setBadgesCount(badgesCount);

        return accountProfile;
    }
}
