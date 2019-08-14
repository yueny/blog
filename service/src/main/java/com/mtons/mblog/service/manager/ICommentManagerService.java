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

import com.mtons.mblog.bo.CommentBo;

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
	 * 根据 articleBlogId 删除所有评论，仅当博文被删除时才操作
	 */
	void deleteByPostId(String articleBlogId);

	/**
	 * 发表博文
	 *
	 * @param comment 博文内容
	 * @return 博文主键
	 */
	Long post(CommentBo comment);
}
