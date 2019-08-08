/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.manager;

import java.util.Set;

/**
 * 评论管理服务
 */
public interface ICommentManagerService {
	/**
	 * 删除评论列表
	 */
	void delete(Set<Long> ids);
	/**
	 * 删除评论，只允许自己删自己的评论
	 */
	void delete(long id, String uid);
	/**
	 * 根据postId删除所有评论，仅当博文被删除时才操作
	 */
	void deleteByPostId(long postId);
}
