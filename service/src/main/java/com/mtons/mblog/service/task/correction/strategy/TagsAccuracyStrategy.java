//package com.mtons.mblog.service.task.correction.strategy;
//
//import com.mtons.mblog.bo.FeatureStatisticsPostBo;
//import com.mtons.mblog.bo.PostBo;
//import com.mtons.mblog.bo.TagBO;
//import com.mtons.mblog.service.BaseService;
//import com.mtons.mblog.service.atom.bao.*;
//import com.mtons.mblog.service.manager.IFavoriteManagerService;
//import com.mtons.mblog.service.task.correction.DataAccuracyCorrectionType;
//import com.mtons.mblog.service.task.correction.DataAccuracyResult;
//import com.mtons.mblog.service.util.PageHelper;
//import com.yueny.rapid.lang.common.enums.EnableType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * 标签维度的统计和特征数据
// *
// * 标签的文章数、mto_post、mto_post_tag、mto_tag 【DataAccuracyCorrectionType.TAGS】
// */
//@Service
//public class TagsAccuracyStrategy extends BaseService implements IAccuracyStrategy {
//    @Autowired
//    private PostService postService;
//    @Autowired
//    private IFavoriteManagerService favoriteManagerService;
//    @Autowired
//    private PostTagService postTagService;
//    @Autowired
//    private TagService tagService;
//    @Autowired
//    private FeatureStatisticsPostAtomService featureStatisticsPostAtomService;
//
//    @Override
//    public DataAccuracyResult featureStatistics() {
//        AtomicLong updates = new AtomicLong(0);
//        AtomicLong inserts = new AtomicLong(0);
//
//        // 获取所有标签
//        Pageable pageable = PageHelper.wrapPageableForSpring(1, 50);
//        Page<TagBO> pageList = tagService.findAll(pageable);
//        tagsFeatureStatistics(pageList.getContent(), inserts, updates);
//        while(pageList.hasNext()){
//            pageable = PageHelper.wrapPageableForSpring(pageable.getPageNumber() + 1, 50);
//            pageList = tagService.findAll(pageable);
//            tagsFeatureStatistics(pageList.getContent(), inserts, updates);
//        }
//
//        DataAccuracyResult result = new DataAccuracyResult();
//        result.setCondition(getCondition());
//        result.setInserts(inserts.get());
//        result.setUpdates(updates.get());
//        result.setSourceTotal(pageList.getTotalElements());
//        return result;
//    }
//
//    @Override
//    public DataAccuracyCorrectionType getCondition() {
//        return DataAccuracyCorrectionType.TAGS;
//    }
//
//    private void tagsFeatureStatistics(List<TagBO> tagsList, AtomicLong inserts, AtomicLong updates){
//        tagsList.parallelStream().forEach(tag -> {
//
//// * 标签的文章数、mto_post、mto_post_tag、mto_tag 【DataAccuracyCorrectionType.TAGS】
//
//            // 统计该 tag 特征
//            // 文章评论数
//            int newCommentsCount = commentService.countBy(post.getId());
//            // 文章收藏数（不同用户）
//            int newFavorsCount = favoriteService.countBy(post.getId());
//            EnableType newType = EnableType.byCode(post.getStatus());
//
//            // 查询该博文是否存在 特征信息
//            FeatureStatisticsPostBo featureStatisticsPostBo = featureStatisticsPostAtomService.findByPostId(post.getId());
//            if(featureStatisticsPostBo == null){
//                // 新增
//                featureStatisticsPostBo = new FeatureStatisticsPostBo();
//                featureStatisticsPostBo.setStatus(newType);
//                featureStatisticsPostBo.setComments(newCommentsCount);
//                featureStatisticsPostBo.setFavors(newFavorsCount);
//                featureStatisticsPostBo.setPostId(post.getId());
//                featureStatisticsPostBo.setPostAuthorUid(post.getUid());
//
//                featureStatisticsPostAtomService.insert(featureStatisticsPostBo);
//
//                inserts.incrementAndGet();
//            }else{
//                // 存在且不一致，则更新
//                if(compareAndUpdate(newType, newCommentsCount, newFavorsCount, featureStatisticsPostBo)){
//                    logger.debug("数据存在差异，存在且不一致，则更新");
//
//                    featureStatisticsPostBo.setStatus(newType);
//                    featureStatisticsPostBo.setComments(newCommentsCount);
//                    featureStatisticsPostBo.setFavors(newFavorsCount);
//
//                    featureStatisticsPostAtomService.updateById(featureStatisticsPostBo);
//
//                    updates.incrementAndGet();
//                }
//            }
//        });
//    }
//
//}
