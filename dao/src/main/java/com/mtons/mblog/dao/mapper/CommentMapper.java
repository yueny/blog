/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.Comment;

/**
 *
 */
public interface CommentMapper extends BaseMapper<Comment> {
//	@Select(value = "SELECT * FROM Comment WHERE amount <= 0 AND update_time < #{time} ")
//	List<Comment> find0Before(@Param("time") String time);

//	Page<Comment> findAll(Pageable pageable);
//	Page<Comment> findAllByPostId(Pageable pageable, long postId);
//	Page<Comment> findAllByAuthorId(Pageable pageable, long authorId);
//
//	List<Comment> removeByIdIn(Collection<Long> ids);
//	List<Comment> removeByPostId(long postId);
//
//	long countByAuthorIdAndPostId(long authorId, long postId);
}
