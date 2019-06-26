/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.base.enums.AuthoredType;
import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

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
public class Comment implements IEntry {
    /** 自然主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 表创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date created;

//    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    @Generated(GenerationTime.ALWAYS)
//    @Temporal(value = TemporalType.TIMESTAMP)
//    private Date updated;

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
