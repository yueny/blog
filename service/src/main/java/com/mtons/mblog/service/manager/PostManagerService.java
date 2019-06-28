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

import com.mtons.mblog.model.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * 文章管理
 * @author langhsu
 *
 */
public interface PostManagerService {
	/**
	 * 分页查询所有文章
	 * 
	 * @param pageable
	 * @param channelIds 分组Id
	 */
	Page<PostVO> paging(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds);

	Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title);
	
	/**
	 * 查询个人发布文章
	 * @param pageable
	 * @param userId
	 */
	Page<PostVO> pagingByAuthorId(Pageable pageable, long userId);
//
//	/**
//	 * 查询最近更新 - 按发布时间排序
//	 * @param maxResults
//	 * @return
//	 */
//	@Cacheable(key = "'latest_' + #maxResults")
//	List<PostBO> findLatestPosts(int maxResults);
//
//	/**
//	 * 查询热门文章 - 按浏览次数排序
//	 * @param maxResults
//	 * @return
//	 */
//	@Cacheable(key = "'hottest_' + #maxResults")
//	List<PostBO> findHottestPosts(int maxResults);

	/**
	 * 根据Ids查询
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<Long, PostVO> findMapByIds(Set<Long> ids);

	/**
	 * 发布文章
	 * @param post
	 */
	Long post(PostVO post);

	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	PostVO get(long id);

	/**
	 * 文章详情
	 * @param articleBlogId
	 * @return
	 */
	PostVO get(String articleBlogId);

	/**
	 * 更新文章方法
	 * @param p
	 */
	boolean update(PostVO p);
//
//	/**
//	 * 推荐/消荐
//	 * @param articleBlogId
//	 * @param featuredType 0: 消荐, 1: 推荐
//	 */
//	@CacheEvict(allEntries = true)
//	void updateFeatured(String articleBlogId, BlogFeaturedType featuredType);
//
//	/**
//	 * 置顶/消顶
//	 * @param articleBlogId
//	 * @param weighted 入参0: 消顶, 1: 置顶
//	 */
//	@CacheEvict(allEntries = true)
//	void updateWeight(String articleBlogId, int weighted);

	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param articleBlogId
	 * @param authorId
	 */
	void delete(String articleBlogId, long authorId);

	/**
	 * 批量删除文章, 且刷新缓存
	 *
	 * @param articleBlogIds
	 */
	void delete(Set<String> articleBlogIds);

//	/**
//	 * 自增浏览数
//	 * @param articleBlogId
//	 */
//	@CacheEvict(key = "'view_' + #articleBlogId")
//	void identityViews(String articleBlogId);
//
//	/**
//	 * 自增评论数
//	 * @param articleBlogId
//	 */
//	@CacheEvict(key = "'view_' + #articleBlogId")
//	void identityComments(String articleBlogId);
//
//	/**
//	 * 喜欢文章
//	 * @param uid
//	 * @param articleBlogId
//	 */
//	@CacheEvict(key = "'view_' + #articleBlogId")
//	void favor(String uid, String articleBlogId);
//
//	/**
//	 * 取消喜欢文章
//	 * @param uid
//	 * @param articleBlogId
//	 */
//	@CacheEvict(key = "'view_' + #articleBlogId")
//	void unfavor(String uid, String articleBlogId);
//
//	long count();
}
