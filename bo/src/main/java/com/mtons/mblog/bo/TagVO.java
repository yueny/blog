package com.mtons.mblog.bo;

import com.yueny.superclub.api.pojo.IBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : langhsu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TagVO implements IBo, Serializable {
    private static final long serialVersionUID = -7787865229252467418L;

    private long id;

    /**
     * tag name
     */
    private String name;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 描述
     */
    private String description;

    /**
     * 最近增加的一遍博文ID
     */
    private long latestPostId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 博文数量
     */
    private int posts;

    /**
     * 最近增加的一遍博文信息
     */
    private PostBO post;
}
