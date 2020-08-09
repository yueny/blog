/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.CommentBo;
import com.mtons.mblog.entity.bao.Comment;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
public interface CommentService extends IPlusBizService<CommentBo, Comment> {
	/**
	 * 管理后台获取评论列表
	 */
	Page<CommentBo> paging4Admin(Pageable pageable);

	/**
	 * 获取作者ID发布过的评论列表
	 *
	 * @param authorId 作者id, 默认为0
	 */
	Page<CommentBo> pagingByAuthorId(Pageable pageable, long authorId);

	/**
	 * 获取博文的评论列表
	 * @param pageable
	 * @param postId 博文id
	 */
	Page<CommentBo> pagingByPostId(Pageable pageable, long postId);

	/**
	 * 取最新的评论列表
	 */
	List<CommentBo> findLatestComments(int maxResults);

	/**
	 * 查看博文的所有评论
	 */
	@Deprecated
	List<CommentBo> findByPostId(Long postId);
	/**
	 * 查看博文的所有评论
	 */
	List<CommentBo> findByPostId(String articleBlogId);

	/**
	 * 根据评论主键查询
	 */
	Map<Long, CommentBo> findByIds(Set<Long> ids);

	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(CommentBo comment);

	/**
	 * 删除评论，只允许自己删自己的评论
	 */
	void delete(long id, String uid);

	/**
	 * 根据 articleBlogId 删除所有评论，仅当博文被删除时才操作
	 */
	void deleteByPostId(String articleBlogId);

	/**
	 * 根据条件统计该用户的该博文留言数
	 *
	 * @param authorId
	 * @param postId
	 * @return
	 */
	int countBy(long authorId, long postId);

	/**
	 * 根据条件统计该博文的留言数
	 *
	 * @param postId
	 * @return
	 */
	int countBy(long postId);
}
