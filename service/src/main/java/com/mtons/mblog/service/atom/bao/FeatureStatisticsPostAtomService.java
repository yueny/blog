package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.FeatureStatisticsPostBo;
import com.mtons.mblog.entity.bao.FeatureStatisticsPostEntry;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;

/**
 * 博文特征 atom 原子服务
 */
public interface FeatureStatisticsPostAtomService extends IPlusBizService<FeatureStatisticsPostBo, FeatureStatisticsPostEntry> {
    /**
     * 根据博文id 查询博文特征信息
     *
     * @param postId  博文id
     * @return
     */
    FeatureStatisticsPostBo findByPostId(Long postId);

    /**
     * 自增评论数
     * @param postId  博文id
     */
    void identityComments(Long postId, String uid);

    /**
     * 自增评论数， 评论数既会增加也会减少
     *
     * @param postId 博文id
     * @param plus 是否为自增步进。 true为自增步进。
     *     自增步进（IDENTITY_STEP=1）还是递减（DECREASE_STEP=-1）
     */
    void identityComments(Long postId, String uid, boolean plus);

    /**
     * 喜欢文章
     * @param uid
     * @param postId
     */
    void favor(Long postId, String uid);

    /**
     * 取消喜欢文章
     * @param uid
     * @param postId
     */
    void unfavor(Long postId, String uid);

}
