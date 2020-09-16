package com.mtons.mblog.service.task.correction;

import com.mtons.mblog.service.AbstractService;
import com.mtons.mblog.service.task.correction.strategy.IAccuracyStrategy;
import com.yueny.superclub.util.strategy.container.StrategyContainerImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据准确性订正
 *
 * 1、文章的留言数和文章收藏数（收藏该文章的用户数）, 存储于 feature_statistics_post，
 *      涉及表： mto_post、mto_comment、mto_favorite 【DataAccuracyCorrectionType.POST】
 *
 * 2、标签的文章数, 存储于 xxx，
 *      涉及表： mto_post、mto_post_tag、mto_tag 【DataAccuracyCorrectionType.TAGS】
 *
 * 3、我的发布文章数、我的收藏数、我的发布评论数, 存储于 feature_statistics_user，
 *      涉及表： mto_post、mto_user、mto_comment、mto_favorite 【DataAccuracyCorrectionType.USER】
 *
 *
 * [图片分布暂不考虑]
 */
@Service
public class DataAccuracyCorrectionTask extends AbstractService {
    private StrategyContainerImpl<DataAccuracyCorrectionType, IAccuracyStrategy> container = new StrategyContainerImpl() {
    };

    /**
     * 每天0-4点执行
     */
    @Scheduled(fixedRateString="20000")//每20秒执行一次
//    @Scheduled(cron="0 0 1-4 * * ?")
    @Async(value = "taskJobExecutor")
    public void postFeatureStatistics(){
        logger.debug("【数据准确性订正】标签的文章数 订正开始。");

        List<DataAccuracyResult> results = new ArrayList<>(DataAccuracyCorrectionType.values().length);
        Arrays.asList(DataAccuracyCorrectionType.values())
                .parallelStream()
                .forEach(type -> {
                    IAccuracyStrategy strategy = container.getStrategy(type);

                    if(strategy != null){
                        DataAccuracyResult result = strategy.featureStatistics();
                        results.add(result);
                    }
                });

        AtomicLong inserts = new AtomicLong(0);
        AtomicLong updates = new AtomicLong(0);
        AtomicLong total = new AtomicLong(0);
        results.stream().forEach(result -> {
            if(result == null){
                return;
            }

            inserts.addAndGet(result.getInserts());
            updates.addAndGet(result.getUpdates());
            total.addAndGet(result.getSourceTotal());
        });

        logger.info("【数据准确性订正】标签的文章数 订正完成， 总数据量:{}, 处理新增数据:{}， 处理更新数据:{}.",
                total, inserts, updates);
    }

}
