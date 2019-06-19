/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.data.ResourceVO;
import com.mtons.mblog.modules.entity.Resource;
import com.mtons.mblog.modules.repository.ResourceRepository;
import com.mtons.mblog.modules.service.ResourceService;
import com.yueny.rapid.lang.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 图片资源管理
 */
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl extends BaseService implements ResourceService {
	@Autowired
	private ResourceRepository resourceRepository;


    @Override
    public ResourceVO findByMd5(String md5) {
        Resource entry = resourceRepository.findByMd5(md5);

        if(entry == null){
            return null;
        }
        return map(entry, ResourceVO.class);
    }

    @Override
    public ResourceVO findByThumbnailCode(String thumbnailCode) {
        Resource entry = resourceRepository.findByThumbnailCode(thumbnailCode);

        if(entry == null){
            return null;
        }
        return map(entry, ResourceVO.class);
    }

    @Override
    public List<ResourceVO> findByMd5In(List<String> md5) {
        List<Resource> entrys = resourceRepository.findByMd5In(md5);

        if(CollectionUtils.isEmpty(entrys)){
            return Collections.emptyList();
        }
        return map(entrys, ResourceVO.class);
    }

    @Override
    public List<ResourceVO> find0Before(String time) {
        List<Resource> entrys = resourceRepository.find0Before(time);

        if(CollectionUtils.isEmpty(entrys)){
            return Collections.emptyList();
        }
        return map(entrys, ResourceVO.class);
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
