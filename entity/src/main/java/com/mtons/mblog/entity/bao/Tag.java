package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author : langhsu
 */
@Entity
@TableName("mto_tag")
@ToString
@Getter
@Setter
public class Tag extends AbstractUpdatePlusEntry {
    /**
     * tag name
     */
    @TableField
    @Column(unique = true, nullable = false, updatable = false, length = 32)
    private String name;

    /**
     * 缩略图
     */
    @TableField
    @Column(length = 128)
    @Deprecated
    private String thumbnail;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 最近增加的一遍博文ID
     */
    @TableField
    private Long latestPostId;

    /**
     * 博文数量
     */
    @TableField
    private Integer posts;

}
