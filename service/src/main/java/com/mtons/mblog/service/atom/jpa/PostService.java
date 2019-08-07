/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.jpa;

import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.entity.jpa.Post;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章管理
 * @author langhsu
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_POST)
public interface PostService extends IPlusBizService<PostBO, Post> {
	/**
	 * 根据 博文编号 articleBlogId 查询c
	 *
	 * 无其他要素添加
	 *
	 * @param articleBlogId  博文编号
	 * @return  博文编号
	 */
	PostBO getByArticleBlogId(String articleBlogId);

	/**
	 * 分页查询所有文章
	 * 
	 * @param pageable
	 * @param channelIds 分组Id
	 */
	@Cacheable
	Page<PostBO> pagingForAuthor(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds);

	Page<PostBO> paging4AdminForAuthor(Pageable pageable, int channelId, String title);

//	/**
//	 * 查询个人发布文章
//	 * @param pageable
//	 * @param userId
//	 */
//	@Cacheable
//	Page<PostBO> pagingByAuthorId(Pageable pageable, long userId);

	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable
	@Deprecated
	List<PostBO> findLatestPosts(int maxResults);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'hottest_' + #maxResults")
	@Deprecated
	List<PostBO> findHottestPosts(int maxResults);

	/**
	 * 根据Ids查询
	 * @param ids
	 * @return <id, 文章对象>
	 */
	@Deprecated
	Map<Long, PostBO> findMapByIds(Set<Long> ids);

	/**
	 * 查询个人发布文章
	 * @param pageable
	 * @param authorId  用户主键ID
	 */
	Page<PostBO> findAllByAuthorId(Pageable pageable, long authorId);

	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@Cacheable(key = "'post_' + #id")
    PostBO getForAuthor(long id);

	/**
	 * 文章详情
	 * @param articleBlogId
	 * @return
	 */
	@Cacheable(key = "'post_' + #articleBlogId")
    PostBO getForAuthor(String articleBlogId);

	/**
	 * 发布文章
	 * @param post
	 */
	@CacheEvict(allEntries = true)
	Long post(PostBO post);

	/**
	 * 更新文章方法
	 * @param p
	 */
	@CacheEvict(allEntries = true)
	@Deprecated
	void update(PostBO p);

	/**
	 * 推荐/消荐
	 * @param articleBlogId
	 * @param featuredType 0: 消荐, 1: 推荐
	 */
	@CacheEvict(allEntries = true)
	@Deprecated
	void updateFeatured(String articleBlogId, BlogFeaturedType featuredType);

	/**
	 * 置顶/消顶
	 * @param articleBlogId
	 * @param weighted 入参0: 消顶, 1: 置顶
	 */
	@CacheEvict(allEntries = true)
	@Deprecated
	void updateWeight(String articleBlogId, int weighted);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param articleBlogId
	 * @param authorId
	 */
	@CacheEvict(allEntries = true)
	@Deprecated
	void delete(String articleBlogId, long authorId);

	/**
	 * 批量删除文章, 且刷新缓存
	 *
	 * @param articleBlogIds
	 * @return 删除的主键列表
	 */
	@CacheEvict(allEntries = true)
	@Deprecated
	Set<Long>  delete(Set<String> articleBlogIds);
	
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
}
