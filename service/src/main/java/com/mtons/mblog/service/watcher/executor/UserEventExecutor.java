/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.watcher.executor;

import com.mtons.mblog.base.consts.Consts;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Set;

/**
 * @author langhsu on 2015/8/6.
 */
public interface UserEventExecutor {
    /**
     * 自增发布文章数
     * @param uid
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityPost(String uid, boolean plus);

    /**
     * 自增评论数
     * @param uid
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(String uid, boolean plus);

    /**
     * 批量自动评论数
     * @param uids
     * @param plus
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(Set<String> uids, boolean plus);
}
