/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.entity;

import com.mtons.mblog.base.api.IEntry;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class Comment implements IEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 父评论ID
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

    @Column(name = "created")
    private Date created;

    /**
     * 用户ID（不是用户uid）。 当为0时则表示为匿名用户
     */
    @Column(name = "author_id")
    private long authorId;

    private int status;

}
