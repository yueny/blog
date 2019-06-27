package com.mtons.mblog.bo;

import com.mtons.mblog.bo.PostBO;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : langhsu
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PostTagVO extends AbstractMaskBo implements Serializable {
    private static final long serialVersionUID = 73354108587481371L;

    private long id;

    private long postId;

    private long tagId;

    private long weight;

    // 扩展
    private PostBO post;
}
