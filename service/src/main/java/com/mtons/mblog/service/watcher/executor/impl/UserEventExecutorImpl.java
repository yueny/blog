/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.watcher.executor.impl;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.dao.repository.UserRepository;
import com.mtons.mblog.service.watcher.executor.UserEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

/**
 * 用户事件操作, 用于统计用户信息
 * @author langhsu
 */
@Service
@Transactional
public class UserEventExecutorImpl implements UserEventExecutor {
    @Autowired
    private UserRepository userMapper;

    /**
     * 自增发布文章数
     * @param uid
     */
    @Override
    public void identityPost(String uid, boolean plus) {
        userMapper.updatePosts(uid, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP, Calendar.getInstance().getTime());
    }

    /**
     * 自增评论数
     * @param uid
     */
    @Override
    public void identityComment(String uid, boolean plus) {
        userMapper.updateComments(Collections.singleton(uid), (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP, Calendar.getInstance().getTime());
    }

    /**
     * 批量自增评论数
     * @param uids
     * @param plus
     */
    @Override
    public void identityComment(Set<String> uids, boolean plus) {
        userMapper.updateComments(uids, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP, Calendar.getInstance().getTime());
    }

}
