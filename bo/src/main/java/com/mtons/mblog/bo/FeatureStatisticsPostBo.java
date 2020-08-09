package com.mtons.mblog.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yueny.rapid.lang.common.enums.EnableType;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Table 博文特征表    Blog post Feature Statistics
 */
@Getter
@Setter
public class FeatureStatisticsPostBo extends AbstractBo implements IBo {

    /** 表修改时间 */
    @JSONField(name="updated", serialize = true, format = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    /**
     * 文章id
     */
    private Long postId;

    /**
     * 文章所属人uid
     */
    private String postAuthorUid; // 作者

    /**
     * 文章评论数。 默认0
     */
    private Integer comments = 0;

    /**
     * 文章收藏数（不同用户）. 默认0
     */
    private Integer favors = 0;

    /**
     * 状态，1有效，0无效. 默认有效
     */
    private EnableType status = EnableType.ENABLE;
}
