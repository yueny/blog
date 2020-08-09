package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Table 博文特征表    Blog post Feature Statistics
 */
@Entity
@TableName("feature_statistics_post")
@Getter
@Setter
public class FeatureStatisticsPostEntry extends AbstractUpdatePlusEntry {
    /**
     * 文章id
     */
    @TableField
    private Long postId;

    /**
     * 文章所属人uid
     */
    @TableField(value = "post_author_uid")
    private String postAuthorUid; // 作者

    /**
     * 文章评论数
     */
    @TableField(value = "post_comments")
    private Integer comments;

    /**
     * 文章收藏数（不同用户）
     */
    @TableField(value = "post_favors")
    private Integer favors;

    /**
     * 状态，1有效，0无效
     */
    @TableField
    private Integer status;
}
