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

import com.mtons.mblog.bo.ResourceVO;
import com.mtons.mblog.entity.Resource;
import com.mtons.mblog.modules.repository.ResourceRepository;
import com.mtons.mblog.service.ResourceManagerService;
import com.yueny.rapid.lang.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 图片资源管理
 */
@Service
@Transactional
public class ResourceManagerServiceImpl extends BaseService implements ResourceManagerService {
	@Autowired
	private ResourceRepository resourceRepository;

    @Override
    public int updateAmount(Collection<String> md5s, long increment) {
        return resourceRepository.updateAmount(md5s, increment);
    }

    @Override
    public int updateAmountByIds(Collection<Long> ids, long increment) {
        return resourceRepository.updateAmountByIds(ids, increment);
    }

    @Override
    public String save(ResourceVO resourceVO) {
        if(resourceVO == null){
            return null;
        }

        if(StringUtils.isEmpty(resourceVO.getThumbnailCode())){
            resourceVO.setThumbnailCode(UuidUtil.getUUIDForNumber32());
        }

        Resource entry = map(resourceVO, Resource.class);

        Resource resource = resourceRepository.save(entry);
        return resource.getThumbnailCode();
    }
}
