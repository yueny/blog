/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.dao.repository.UserRepository;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.service.comp.IPasswdService;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.bo.BadgesCount;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.User;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.seq.container.ISeqContainer;
import com.mtons.mblog.service.atom.MessageService;
import com.mtons.mblog.service.atom.UserService;
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
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    private UserRepository userMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private IPasswdService passwdService;
    @Autowired
    private ISeqContainer seqContainer;

    @Override
    public Page<UserBO> paging(Pageable pageable, String name) {
        Page<User> page = userMapper.findAll((root, query, builder) -> {
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

        Iterable<User> list = userMapper.findAllById(ids);
        Map<Long, UserBO> ret = new HashMap<>();

        list.forEach(po -> {
            UserBO userBO = map(po, UserBO.class);
            userBO.setPassword("");
            ret.put(po.getId(), userBO);
        });

        return ret;
    }

    @Override
    @Transactional
    public AccountProfile login(String username, String password) {
        User po = userMapper.findByUsername(username);
        Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");

        Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

        po.setLastLogin(Calendar.getInstance().getTime());
        userMapper.save(po);

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
        User po = userMapper.findById(id).get();

        Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
        po.setLastLogin(Calendar.getInstance().getTime());

        AccountProfile accountProfile = mapAny(po, AccountProfile.class);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(accountProfile.getId()));

        accountProfile.setBadgesCount(badgesCount);

        return accountProfile;
    }

    @Override
    public UserBO register(UserBO user) {
        User entry = map(user, User.class);
        entry.setCreated(Calendar.getInstance().getTime());
        entry.setUpdated(Calendar.getInstance().getTime());

        userMapper.save(entry);

        return user;
    }

    @Override
    @Transactional
    public AccountProfile update(UserBO user) {
        User po = userMapper.findById(user.getId()).get();
        po.setName(user.getName());
        po.setSignature(user.getSignature());

        if(StringUtils.isNotEmpty(user.getDomainHack())){
            po.setDomainHack(user.getDomainHack());
        }else{
            po.setDomainHack(String.valueOf(user.getId()));
        }

        po.setUpdated(Calendar.getInstance().getTime());
        userMapper.save(po);

        // BeanMapUtils.copyPassport(po);
        return mapAny(po, AccountProfile.class);
    }

    @Override
    @Transactional
    public AccountProfile updateEmail(long id, String email) {
        User po = userMapper.findById(id).get();

        if (email.equals(po.getEmail())) {
            throw new MtonsException("邮箱地址没做更改");
        }

        User check = userMapper.findByEmail(email);

        if (check != null && check.getId() != po.getId()) {
            throw new MtonsException("该邮箱地址已经被使用了");
        }
        po.setEmail(email);
        po.setUpdated(Calendar.getInstance().getTime());
        userMapper.save(po);

        return mapAny(po, AccountProfile.class);
    }

    @Override
    public UserBO get(long userId) {
        Optional<User> optional = userMapper.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            if (user == null) {
                return null;
            }

            return map(user, UserBO.class);
        }
        return null;
    }

    @Override
    public UserBO get(String uid) {
        User user = userMapper.findByUid(uid);
        if (user == null) {
            return null;
        }

        return map(user, UserBO.class);
    }

    @Override
    public UserBO getByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        return map(user, UserBO.class);
    }

    @Override
    public UserBO getByEmail(String email) {
        return BeanMapUtils.copy(userMapper.findByEmail(email));
    }

    @Override
    @Transactional
    public AccountProfile updateAvatar(long id, String path) {
        User po = userMapper.findById(id).get();
        po.setAvatar(path);
        po.setUpdated(Calendar.getInstance().getTime());

        userMapper.save(po);

        return mapAny(po, AccountProfile.class);
    }

    @Override
    @Transactional
    public boolean updatePassword(String uid, String newPassword) throws InvalidException{
        Assert.hasLength(newPassword, "密码不能为空!");

        return userMapper.changePassword(uid, newPassword, Calendar.getInstance().getTime()) == 1;
    }

    @Override
    @Transactional
    public boolean updateStatus(long id, int status) {
        User po = userMapper.findById(id).get();

        po.setStatus(status);
        return userMapper.updateStatus(id, status, Calendar.getInstance().getTime()) == 1;
    }

    @Override
    public long count() {
        return userMapper.count();
    }

    @Override
    public UserBO getByDomainHack(String domainHack) {
        User user = userMapper.findByDomainHack(domainHack);
        if (user == null) {
            return null;
        }

        return map(user, UserBO.class);
    }

}
