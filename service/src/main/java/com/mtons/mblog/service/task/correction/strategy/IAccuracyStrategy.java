package com.mtons.mblog.service.task.correction.strategy;

import com.mtons.mblog.service.task.correction.DataAccuracyCorrectionType;
import com.mtons.mblog.service.task.correction.DataAccuracyResult;
import com.yueny.superclub.util.strategy.IStrategy;


public interface IAccuracyStrategy extends IStrategy<DataAccuracyCorrectionType> {
    /**
     *
     * @return <新增数, 修改数></>
     */
    DataAccuracyResult featureStatistics();
}
