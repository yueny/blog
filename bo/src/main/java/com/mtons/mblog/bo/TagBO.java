package com.mtons.mblog.bo;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/17 下午11:26
 *
 */
@EqualsAndHashCode(callSuper = false)
public class TagBO extends AbstractMaskBo implements IBo, Serializable {
    private static final long serialVersionUID = -7787865229252467418L;

    @Getter
    @Setter
    private long id;

    /**
     * tag name
     */
    @Getter
    @Setter
    private String name;

    /**
     * 缩略图
     */
    @Deprecated
    @Getter
    @Setter
    private String thumbnail;

    /**
     * 描述
     */
    @Getter
    @Setter
    private String description;

    /**
     * 最近增加的一遍博文ID
     */
    @Getter
    @Setter
    private long latestPostId;

    /**
     * 创建时间
     @Getter
     @Setter
     */
    @Getter
    @Setter
    private Date created;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date updated;

    /**
     * 博文数量
     */
    @Getter
    @Setter
    private int posts;

    ////////////// 扩展
    /**
     * 最近增加的一遍博文信息
     */
    @Getter
    @Setter
    private PostBo post;
}
