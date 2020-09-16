package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.FeatureStatisticsPostEntry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 博文特征 mapper
 */
public interface FeatureStatisticsPostMapper extends BaseMapper<FeatureStatisticsPostEntry> {
    /**
     * 喜欢文章
     */
    @Update("update feature_statistics_post set favors = favors + #{increment} where post_id = #{postId} and user_uid = #{uid}")
    void updateFavors(@Param("postId") Long postId, @Param("uid") String uid, @Param("increment") int increment);

    /**
     * 自增评论数
     */
    @Update("update feature_statistics_post set comments = comments + #{increment} where post_id = #{postId} and user_uid = #{uid}")
    void updateComments(@Param("postId") Long postId, @Param("uid") String uid, @Param("increment") int increment);
}
