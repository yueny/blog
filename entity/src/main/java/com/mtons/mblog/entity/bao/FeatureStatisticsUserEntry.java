package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Table 用户特征表， 用户维度    User Feature Statistics
 */
@Entity
@TableName("feature_statistics_user")
@Getter
@Setter
public class FeatureStatisticsUserEntry extends AbstractUpdatePlusEntry {
    /**
     * 文章id
     */
    @TableField
    private Long postId;

    /**
     * 文章所属人uid
     */
    @TableField(value = "user_uid")
    private String userUid; // 作者

    /**
     * 文章评论数
     */
    @TableField(value = "comments")
    private Integer comments;

    /**
     * 文章收藏数（不同用户）
     */
    @TableField(value = "favors")
    private Integer favors;

    /**
     * 状态，1有效，0无效
     */
    @TableField
    private Integer status;

}
