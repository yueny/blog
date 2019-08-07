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
import com.mtons.mblog.entity.jpa.Post;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 新post 持久层服务， 代替 PostRepository
 */
public interface PostMapper extends BaseMapper<Post> {
//    /**
//     * 查询指定博文列表
//     *
//     * @param articleBlogIds 博文编号
//     * @return
//     */
//    List<Post> findAllByArticleBlogId(Set<String> articleBlogIds);
//
//    /**
//     * 根据 博文编号 articleBlogId 查询博文信息
//     */
//    Post findByArticleBlogId(String articleBlogId);
//
//    /**
//     * 查询指定用户
//     *
//     * @param pageable
//     * @param uid 用户编号
//     * @return
//     */
//    Page<Post> findAllByUid(Pageable pageable, String uid);

    /**
     * 查询最大权重
     */
    @Select("select coalesce(max(weight), 0) from mto_post")
    int maxWeight();

    /**
     * 自增浏览数
     */
    @Update("update mto_post set views = views + #{increment} where article_blog_id = #{articleBlogId}")
    void updateViews(@Param("articleBlogId") String articleBlogId, @Param("increment") int increment);

    /**
     * 喜欢文章
     */
    @Update("update mto_post set favors = favors + #{increment} where article_blog_id = #{articleBlogId}")
    void updateFavors(@Param("articleBlogId") String articleBlogId, @Param("increment") int increment);

    /**
     * 自增评论数
     */
    @Update("update mto_post set comments = comments + #{increment} where article_blog_id = #{articleBlogId}")
    void updateComments(@Param("articleBlogId") String articleBlogId, @Param("increment") int increment);

}
