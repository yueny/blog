/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
public interface CommentService {
	/**
	 * 管理后台获取评论列表
	 */
	Page<CommentVO> paging4Admin(Pageable pageable);

	/**
	 * 获取作者ID发布过的评论列表
	 *
	 * @param authorId 作者id, 默认为0
	 */
	Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId);

	/**
	 * 获取博文的评论列表
	 * @param pageable
	 * @param postId 博文id
	 */
	Page<CommentVO> pagingByPostId(Pageable pageable, long postId);

	/**
	 * 取最新的评论列表
	 */
	List<CommentVO> findLatestComments(int maxResults);

	/**
	 * 根据评论主键查询
	 */
	Map<Long, CommentVO> findByIds(Set<Long> ids);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(CommentVO comment);

	/**
	 * 删除评论列表
	 */
	void delete(List<Long> ids);

	/**
	 * 删除评论，只允许自己删自己的评论
	 */
	void delete(long id, long authorId);

	/**
	 * 根据postId删除所有评论，仅当博文被删除时才操作
	 */
	void deleteByPostId(long postId);

	long count();

	long countByAuthorIdAndPostId(long authorId, long postId);
}
