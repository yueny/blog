package com.mtons.mblog.service.task.correction.strategy;

import com.mtons.mblog.bo.FeatureStatisticsPostBo;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.*;
import com.mtons.mblog.service.task.correction.DataAccuracyCorrectionType;
import com.mtons.mblog.service.task.correction.DataAccuracyResult;
import com.mtons.mblog.service.util.PageHelper;
import com.yueny.rapid.lang.common.enums.EnableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 博文维度的统计和特征数据
 *
 * 文章的留言数和关注 修正、mto_post、mto_comment 【DataAccuracyCorrectionType.POST】
 */
@Service
public class PostAccuracyStrategy extends BaseService implements IAccuracyStrategy {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private IFavoriteService favoriteService;
    @Autowired
    private FeatureStatisticsPostAtomService featureStatisticsPostAtomService;

    @Override
    public DataAccuracyResult featureStatistics() {
        AtomicLong updates = new AtomicLong(0);
        AtomicLong inserts = new AtomicLong(0);

        // 捞取所有博文
        Pageable pageable = PageHelper.wrapPageableForSpring(1, 50);
        Page<PostBo> pageList = postService.findAll(pageable);
        postFeatureStatistics(pageList.getContent(), inserts, updates);
        while(pageList.hasNext()){
            pageable = PageHelper.wrapPageableForSpring(pageable.getPageNumber() + 1, 50);
            pageList = postService.findAll(pageable);
            postFeatureStatistics(pageList.getContent(), inserts, updates);
        }

        DataAccuracyResult result = new DataAccuracyResult();
        result.setCondition(getCondition());
        result.setInserts(inserts.get());
        result.setUpdates(updates.get());
        result.setSourceTotal(pageList.getTotalElements());
        return result;
    }

    @Override
    public DataAccuracyCorrectionType getCondition() {
        return DataAccuracyCorrectionType.POST;
    }


    private void postFeatureStatistics(List<PostBo> postList, AtomicLong inserts, AtomicLong updates){
        postList.parallelStream().forEach(post -> {
            // 统计该博文特征
            // 文章评论数
            int newCommentsCount = commentService.countBy(post.getId());
            // 文章收藏数（不同用户）
            int newFavorsCount = favoriteService.countBy(post.getId());
            EnableType newType = EnableType.byCode(post.getStatus());

            // 查询该博文是否存在 特征信息
            FeatureStatisticsPostBo featureStatisticsPostBo = featureStatisticsPostAtomService.findByPostId(post.getId());
            if(featureStatisticsPostBo == null){
                // 新增
                featureStatisticsPostBo = new FeatureStatisticsPostBo();
                featureStatisticsPostBo.setStatus(newType);
                featureStatisticsPostBo.setComments(newCommentsCount);
                featureStatisticsPostBo.setFavors(newFavorsCount);
                featureStatisticsPostBo.setPostId(post.getId());
                featureStatisticsPostBo.setPostAuthorUid(post.getUid());

                featureStatisticsPostAtomService.insert(featureStatisticsPostBo);

                inserts.incrementAndGet();
            }else{
                // 存在且不一致，则更新
                if(compareAndUpdate(newType, newCommentsCount, newFavorsCount, featureStatisticsPostBo)){
                    logger.debug("数据存在差异，存在且不一致，则更新");

                    featureStatisticsPostBo.setStatus(newType);
                    featureStatisticsPostBo.setComments(newCommentsCount);
                    featureStatisticsPostBo.setFavors(newFavorsCount);

                    featureStatisticsPostAtomService.updateById(featureStatisticsPostBo);

                    updates.incrementAndGet();
                }
            }
        });
    }

    private boolean compareAndUpdate(EnableType newType, int newCommentsCount, int newFavorsCount, FeatureStatisticsPostBo target){
        if(target.getStatus() != newType){
            return true;
        }

        if(target.getComments() != newCommentsCount){
            return true;
        }

        if(target.getFavors() != newFavorsCount){
            return true;
        }

        // 不更新
        return false;
    }
}
