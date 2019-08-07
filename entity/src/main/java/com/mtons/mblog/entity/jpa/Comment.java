/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.entity.jpa;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

/**
 * 评论
 *
 * @author langhsu
 */
@Entity
@Table(name = "mto_comment", indexes = {
        @Index(name = "IK_POST_ID", columnList = "post_id")
})
@TableName("mto_comment")
@Getter
@Setter
public class Comment extends AbstractJpaEntry implements IEntry {

    /**
     * 针对性回复的评论ID(父评论ID)
     */
    private long pid;

    /**
     * 所属内容ID
     */
    @Column(name = "post_id")
    private long postId;
    /**
     * 文章编号扩展ID
     */
    @Field
    @Column(name = "article_blog_id", length = 64)
    private String articleBlogId;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 用户ID（不是用户uid）。 当为0时则表示为匿名用户
     */
    @Column(name = "author_id")
    private long authorId;
    /**
     * 用户uid。 当为null时则表示为匿名用户
     */
    private String uid;
    /**
     * 是否为鉴权用户。 1为认证用户(默认)， 0为匿名用户
     */
    @Column(name = "commit_authored_type")
    private Integer commitAuthoredType;

    private int status;
    /**
     * 客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * 客户端ip
     */
    @Column(name = "client_agent")
    private String clientAgent;
}
