package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.dao.mapper.UserMapper;
import com.mtons.mblog.entity.bao.User;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.exception.MtonsException;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-14 20:14
 */
@Service
public class UserServiceImpl extends AbstractPlusService<UserBO, User, UserMapper>
        implements UserService {
//    @Autowired
//    private MessageService messageService;

    @Override
    public UserBO register(UserBO user) {
        insert(user);

        return user;
    }

    @Override
    public AccountProfile update(UserBO user) {
        UserBO userBO = get(user.getId());

        userBO.setName(user.getName());
        userBO.setSignature(user.getSignature());

        if(StringUtils.isNotEmpty(user.getDomainHack())){
            userBO.setDomainHack(user.getDomainHack());
        }else{
            userBO.setDomainHack(String.valueOf(user.getId()));
        }

        updateById(userBO);

        return mapAny(userBO, AccountProfile.class);
    }

    @Override
    public AccountProfile updateEmail(long id, String email) {
        UserBO userBO = get(id);

        if (userBO == null) {
            throw new MtonsException("无效用户！");
        }
        if (StringUtils.equals(email, userBO.getEmail())) {
            throw new MtonsException("邮箱地址没做更改");
        }

        UserBO check = findByEmail(email);
        if (check != null && check.getId() != userBO.getId()) {
            throw new MtonsException("该邮箱地址已经被使用了");
        }

        UserBO save = new UserBO();
        save.setId(userBO.getId());
        save.setEmail(email);
        updateById(save);

        return mapAny(userBO, AccountProfile.class);
    }

    @Override
    public UserBO get(String uid) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getUid, uid);

        return get(queryWrapper);
    }

    @Override
    public UserBO findByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getUsername, username);

        return get(queryWrapper);
    }

    @Override
    public UserBO findByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getEmail, email);

        return get(queryWrapper);
    }

    @Override
    public UserBO findByDomainHack(String domainHack) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getDomainHack, domainHack);

        return get(queryWrapper);
    }

    @Override
    public AccountProfile updateAvatar(long id, String path) {
        UserBO userBO = get(id);

        if (userBO == null) {
            throw new MtonsException("无效用户！");
        }

        UserBO save = new UserBO();
        save.setId(userBO.getId());
        save.setAvatar(path);
        updateById(save);

        return mapAny(userBO, AccountProfile.class);
    }

    @Override
    public boolean updatePassword(String uid, String newPassword) throws InvalidException {
        Assert.hasLength(newPassword, "密码不能为空!");

        return baseMapper.changePassword(uid, newPassword, Calendar.getInstance().getTime()) == 1;
    }

    @Override
    public boolean updateStatus(String uid, StatusType status) {
        return baseMapper.updateStatus(uid, status.getValue(), Calendar.getInstance().getTime()) == 1;
    }

    /**
     * 自增发布文章数
     * @param uid
     */
    @Override
    public void identityPost(String uid, boolean plus) {
        baseMapper.updatePosts(uid, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP, Calendar.getInstance().getTime());
    }

    /**
     * 自增评论数
     * @param uid
     */
    @Override
    public void identityComment(String uid, boolean plus) {
        identityComment(Collections.singleton(uid), plus);
    }

    /**
     * 批量自增评论数
     * @param uids
     * @param plus
     */
    @Override
    public void identityComment(Set<String> uids, boolean plus) {
        baseMapper.updateComments(uids, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP, Calendar.getInstance().getTime());
    }
}
