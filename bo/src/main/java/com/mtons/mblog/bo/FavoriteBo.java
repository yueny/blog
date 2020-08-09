package com.mtons.mblog.bo;

import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 收藏记录
 */
@Getter
@Setter
public class FavoriteBo extends AbstractBo implements IBo {

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
}
