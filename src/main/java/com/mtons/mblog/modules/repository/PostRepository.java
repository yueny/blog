/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author langhsu
 */
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    /**
     * 查询指定用户
     *
     * @param pageable
     * @param authorId
     * @return
     */
    Page<Post> findAllByAuthorId(Pageable pageable, long authorId);

    /**
     * 根据 articleBlogId 查询博文信息
     */
    Post findByArticleBlogId(String articleBlogId);

    @Query("select coalesce(max(weight), 0) from Post")
    int maxWeight();

    @Modifying
    @Query("update Post set views = views + :increment where article_blog_id = :articleBlogId")
    void updateViews(@Param("articleBlogId") String articleBlogId, @Param("increment") int increment);

    @Modifying
    @Query("update Post set favors = favors + :increment where id = :id")
    void updateFavors(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Query("update Post set comments = comments + :increment where id = :id")
    void updateComments(@Param("id") long id, @Param("increment") int increment);

}
