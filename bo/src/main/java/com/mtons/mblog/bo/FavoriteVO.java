package com.mtons.mblog.bo;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author langhsu on 2015/8/31.
 */
@Getter
@Setter
public class FavoriteVO extends AbstractMaskBo implements IBo, Serializable {
    private long id;

    /**
     * 所属用户
     */
    private long userId;
    /**
     * 所属用户
     */
    private String uid;

    /**
     * 内容ID
     */
    private long postId;
    /**
     * 文章扩展ID
     */
    private String articleBlogId;

    private Date created;


    // extend
    private PostBO post;
}
