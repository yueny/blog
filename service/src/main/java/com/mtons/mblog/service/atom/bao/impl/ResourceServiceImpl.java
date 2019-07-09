/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.entity.bao.Resource;
import com.mtons.mblog.dao.mapper.ResourceMapper;
import com.mtons.mblog.service.atom.bao.ResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 图片资源管理
 */
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl extends BaseBaoService<ResourceMapper, Resource>
        implements ResourceService {
    @Override
    public ResourceBO findByMd5(String md5) {
        LambdaQueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>().lambda();
        queryWrapper.eq(Resource::getMd5, md5);

        Resource entry = getOne(queryWrapper);

        if(entry == null){
            return null;
        }
        return map(entry, ResourceBO.class);
    }

    @Override
    public ResourceBO findByThumbnailCode(String thumbnailCode) {
        LambdaQueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>().lambda();
        queryWrapper.eq(Resource::getThumbnailCode, thumbnailCode);

        Resource entry = getOne(queryWrapper);

        if(entry == null){
            return null;
        }
        return map(entry, ResourceBO.class);
    }

    @Override
    public List<ResourceBO> findByMd5In(List<String> md5) {
        LambdaQueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>().lambda();
        queryWrapper.in(Resource::getMd5, md5);

        List<Resource> entrys = list(queryWrapper);

        if(CollectionUtils.isEmpty(entrys)){
            return Collections.emptyList();
        }
        return map(entrys, ResourceBO.class);
    }

    @Override
    public List<ResourceBO> find0Before(String time) {
        LambdaQueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>().lambda();
        queryWrapper.le(Resource::getAmount, 0); //amount <= 0
        queryWrapper.lt(Resource::getUpdated, time); //update_time < :time

        List<Resource> entrys = list(queryWrapper);

        if(CollectionUtils.isEmpty(entrys)){
            return Collections.emptyList();
        }
        return map(entrys, ResourceBO.class);
    }

//    @Override
//    public int updateAmount(Collection<String> md5s, long increment) {
//        return resourceRepository.updateAmount(md5s, increment);
//    }
//
//    @Override
//    public int updateAmountByIds(Collection<Long> ids, long increment) {
//        return resourceRepository.updateAmountByIds(ids, increment);
//    }
}
