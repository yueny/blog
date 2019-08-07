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
 * 文章管理服务
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
	
//	/**
//	 * 查询个人发布文章
//	 * @param pageable
//	 * @param userId
//	 */
//	Page<PostVO> pagingByAuthorId(Pageable pageable, long userId);

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
}
