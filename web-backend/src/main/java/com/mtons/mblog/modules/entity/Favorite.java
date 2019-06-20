package com.mtons.mblog.modules.entity;

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 喜欢/收藏
 * @author langhsu on 2015/8/31.
 */
@Entity
@Table(name = "mto_favorite", indexes = {
        @Index(name = "IK_USER_ID", columnList = "user_id")
})
@Getter
@Setter
public class Favorite implements IEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private long userId;
    /**
     * 所属用户
     */
    @Column(name = "uid")
    private String uid;

    /**
     * 内容ID
     */
    @Column(name = "post_id")
    private long postId;
    /**
     * 文章扩展ID
     */
    @Column(name = "article_blog_id", length = 64)
    private String articleBlogId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
}
