/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.dao.repository.UserRepository;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.bo.BadgesCount;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.bao.User;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.atom.jpa.UserJpaService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 用户服务，该服务提供的对象，密码均为空
 */
@Service
public class UserJpaServiceImpl extends BaseService implements UserJpaService {
    @Autowired
    private UserRepository userRepositoryU;
    @Autowired
    private MessageService messageService;

    @Override
    public Page<UserBO> paging(Pageable pageable, String name) {
        Page<User> page = userRepositoryU.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (StringUtils.isNoneBlank(name)) {
                predicate.getExpressions().add(
                        builder.like(root.get("name"), "%" + name + "%"));
            }

            query.orderBy(builder.desc(root.get("id")));
            return predicate;
        }, pageable);

        List<UserBO> rets = new ArrayList<>();
        page.getContent().forEach(n -> {
            UserBO userBO = map(n, UserBO.class);
            userBO.setPassword("");
            rets.add(userBO);
        });
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Map<Long, UserBO> findMapByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        Iterable<User> list = userRepositoryU.findAllById(ids);

        Map<Long, UserBO> maps = new HashMap<>();
        list.forEach(po -> {
            UserBO userBO = map(po, UserBO.class);
            userBO.setPassword("");

            maps.put(po.getId(), userBO);
        });

        return maps;
    }

    @Override
    @Transactional
    public AccountProfile login(String username, String password) {
        User po = userRepositoryU.findByUsername(username);
        Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");

        Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

        po.setLastLogin(Calendar.getInstance().getTime());
        userRepositoryU.save(po);

        AccountProfile u = mapAny(po, AccountProfile.class);
        //AccountProfile u = BeanMapUtils.copyPassport(po);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));

        u.setBadgesCount(badgesCount);
        return u;
    }

    @Override
    @Transactional
    public AccountProfile findProfile(Long id) {
        User po = userRepositoryU.findById(id).get();

        Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
        po.setLastLogin(Calendar.getInstance().getTime());

        AccountProfile accountProfile = mapAny(po, AccountProfile.class);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(accountProfile.getId()));

        accountProfile.setBadgesCount(badgesCount);

        return accountProfile;
    }


}
