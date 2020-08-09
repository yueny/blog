package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.FeatureStatisticsPostBo;
import com.mtons.mblog.dao.mapper.FeatureStatisticsPostMapper;
import com.mtons.mblog.entity.bao.FeatureStatisticsPostEntry;
import com.mtons.mblog.service.atom.bao.FeatureStatisticsPostAtomService;
import com.mtons.mblog.service.atom.bao.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博文特征 atom 原子服务
 */
@Service
public class FeatureStatisticsPostAtomServiceImpl extends AbstractPlusService<FeatureStatisticsPostBo, FeatureStatisticsPostEntry, FeatureStatisticsPostMapper>
        implements FeatureStatisticsPostAtomService {
    @Autowired
    private IFavoriteService favoriteService;

    @Override
    public FeatureStatisticsPostBo findByPostId(Long postId) {
        LambdaQueryWrapper<FeatureStatisticsPostEntry> queryWrapper = new QueryWrapper<FeatureStatisticsPostEntry>().lambda();
        queryWrapper.eq(FeatureStatisticsPostEntry::getPostId, postId);

        FeatureStatisticsPostEntry po = baseMapper.selectOne(queryWrapper);
        if(po == null){
            return  null;
        }

        return map(po, FeatureStatisticsPostBo.class);
    }

    @Override
    @Transactional
    public void identityComments(Long postId, String uid) {
        identityComments(postId, uid, true);
    }

    @Override
    @Transactional
    public void identityComments(Long postId, String uid, boolean plus) {
        if(plus){
            baseMapper.updateComments(postId, uid, Consts.IDENTITY_STEP);
        }else{
            // 减
            baseMapper.updateComments(postId, uid, Consts.DECREASE_STEP);
        }
    }

    @Override
    @Transactional
    public void favor(Long postId, String uid) {
        baseMapper.updateFavors(postId, uid, Consts.IDENTITY_STEP);

        favoriteService.add(uid, postId);
    }

    @Override
    @Transactional
    public void unfavor(Long postId, String uid) {
        baseMapper.updateFavors(postId, uid,  Consts.DECREASE_STEP);

        favoriteService.delete(uid, postId);
    }
}
