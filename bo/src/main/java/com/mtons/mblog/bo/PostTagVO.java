package com.mtons.mblog.bo;

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

    /**
     * 数据主键
     */
    private long id;

    /**
     * 博文主键
     */
    private long postId;

    /**
     * 标签主键
     */
    private long tagId;

    /**
     * 权重
     */
    private long weight;

    // 扩展
    /**
     * 该关系表中的对应博文的博文实体信息
     */
    private PostBo post;
}
