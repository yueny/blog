package com.mtons.mblog.service.task.correction;

/**
 * 数据准确性订正的维度
 */
public enum DataAccuracyCorrectionType {
    /**
     * 标签的文章数、mto_post、mto_post_tag、mto_tag
     */
    TAGS,
    /**
     * 每篇文章的留言数和关注 、mto_post、mto_comment
     */
    POST,
    /**
     * 我的发布和收藏、mto_user、mto_favorite、mto_post
     */
    USER,
    ;
}
