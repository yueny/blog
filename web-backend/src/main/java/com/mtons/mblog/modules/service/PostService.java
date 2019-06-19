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

import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.PostVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章管理
 * @author langhsu
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface PostService {
	/**
	 * 分页查询所有文章
	 * 
	 * @param pageable
	 * @param channelIds 分组Id
	 */
	@Cacheable
	Page<PostVO> paging(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds);

	Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title);
	
	/**
	 * 查询个人发布文章
	 * @param pageable
	 * @param userId
	 */
	@Cacheable
	Page<PostVO> pagingByAuthorId(Pageable pageable, long userId);

	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'latest_' + #maxResults")
	List<PostVO> findLatestPosts(int maxResults);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'hottest_' + #maxResults")
	List<PostVO> findHottestPosts(int maxResults);
	
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
	@CacheEvict(allEntries = true)
	long post(PostVO post);
	
	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@Cacheable(key = "'post_' + #id")
	PostVO get(long id);

	/**
	 * 文章详情
	 * @param articleBlogId
	 * @return
	 */
	@Cacheable(key = "'post_' + #articleBlogId")
	PostVO get(String articleBlogId);

	/**
	 * 更新文章方法
	 * @param p
	 */
	@CacheEvict(allEntries = true)
	void update(PostVO p);

	/**
	 * 推荐/消荐
	 * @param articleBlogId
	 * @param featuredType 0: 消荐, 1: 推荐
	 */
	@CacheEvict(allEntries = true)
	void updateFeatured(String articleBlogId, BlogFeaturedType featuredType);

	/**
	 * 置顶/消顶
	 * @param articleBlogId
	 * @param weighted 入参0: 消顶, 1: 置顶
	 */
	@CacheEvict(allEntries = true)
	void updateWeight(String articleBlogId, int weighted);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param articleBlogId
	 * @param authorId
	 */
	@CacheEvict(allEntries = true)
	void delete(String articleBlogId, long authorId);

	/**
	 * 批量删除文章, 且刷新缓存
	 *
	 * @param articleBlogIds
	 */
	@CacheEvict(allEntries = true)
	void delete(Set<String> articleBlogIds);
	
	/**
	 * 自增浏览数
	 * @param articleBlogId
	 */
	@CacheEvict(key = "'view_' + #articleBlogId")
	void identityViews(String articleBlogId);
	
	/**
	 * 自增评论数
	 * @param articleBlogId
	 */
	@CacheEvict(key = "'view_' + #articleBlogId")
	void identityComments(String articleBlogId);

	/**
	 * 喜欢文章
	 * @param uid
	 * @param articleBlogId
	 */
	@CacheEvict(key = "'view_' + #articleBlogId")
	void favor(String uid, String articleBlogId);

	/**
	 * 取消喜欢文章
	 * @param uid
	 * @param articleBlogId
	 */
	@CacheEvict(key = "'view_' + #articleBlogId")
	void unfavor(String uid, String articleBlogId);

	long count();
}
