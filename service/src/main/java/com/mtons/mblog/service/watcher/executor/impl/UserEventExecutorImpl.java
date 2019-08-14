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

import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.watcher.executor.UserEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

    /**
     * 自增发布文章数
     * @param uid
     */
    @Override
    public void identityPost(String uid, boolean plus) {
        userService.identityPost(uid, plus);
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
        userService.identityComment(uids, plus);
    }

}
