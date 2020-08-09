package com.mtons.mblog.service.task.correction.strategy;

import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.*;
import com.mtons.mblog.service.manager.IFavoriteManagerService;
import com.mtons.mblog.service.task.correction.DataAccuracyCorrectionType;
import com.mtons.mblog.service.task.correction.DataAccuracyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsAccuracyStrategy extends BaseService implements IAccuracyStrategy {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private IFavoriteService favoriteService;
    @Autowired
    private IFavoriteManagerService favoriteManagerService;
    @Autowired
    private PostTagService postTagService;
    @Autowired
    private TagService tagService;
    @Autowired
    private FeatureStatisticsPostAtomService featureStatisticsPostAtomService;

    @Override
    public DataAccuracyResult featureStatistics() {
        return null;
    }

    @Override
    public DataAccuracyCorrectionType getCondition() {
        return DataAccuracyCorrectionType.TAGS;
    }
}
