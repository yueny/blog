/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.bao.Post;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;


/**
 * 已作废，请使用 PostMapper
 */
@Deprecated
public interface PostRepository extends CrudRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    /**
     * 查询指定博文列表
     *
     * @param articleBlogIds 博文编号
     * @returns
     */
    List<Post> findAllByArticleBlogId(Set<String> articleBlogIds);

    /**
     * 根据 博文编号 articleBlogId 查询博文信息
     */
    Post findByArticleBlogId(String articleBlogId);

}
