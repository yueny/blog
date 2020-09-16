package com.mtons.mblog.service.task.correction.strategy;

import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.task.correction.DataAccuracyCorrectionType;
import com.mtons.mblog.service.task.correction.DataAccuracyResult;

public class UserAccuracyStrategy extends BaseService implements IAccuracyStrategy  {
    @Override
    public DataAccuracyResult featureStatistics() {
        return null;
    }

    @Override
    public DataAccuracyCorrectionType getCondition() {
        return DataAccuracyCorrectionType.USER;
    }
}
