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
import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.condition.ViewerQueryCondition;
import com.mtons.mblog.dao.mapper.ViewLogMapper;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
public class ViewLogServiceImpl extends AbstractPlusService<ViewLogVO, ViewLogEntry, ViewLogMapper>
        implements ViewLogService {
    @Override
    public Page<ViewLogVO> findAllByCondition(Pageable pageable, ViewerQueryCondition condition) {
        LambdaQueryWrapper<ViewLogEntry> queryWrapper = new QueryWrapper<ViewLogEntry>().lambda();

        if(StringUtils.isNotEmpty(condition.getClientIp())){
            queryWrapper.eq(ViewLogEntry::getClientIp, condition.getClientIp());
        }
        if(StringUtils.isNotEmpty(condition.getMethod())){
            queryWrapper.eq(ViewLogEntry::getMethod, condition.getMethod());
        }
        if(StringUtils.isNotEmpty(condition.getClientAgent())){
            queryWrapper.like(ViewLogEntry::getClientAgent, condition.getClientAgent());
        }
        if(StringUtils.isNotEmpty(condition.getResourcePath())){
            queryWrapper.like(ViewLogEntry::getResourcePath, condition.getResourcePath());
        }

        if(StringUtils.isNotEmpty(condition.getCreateDate())){
            queryWrapper.ge(ViewLogEntry::getCreated, condition.getCreateDate());
        }
//        if(StringUtils.isNotEmpty(condition.getEclientIps())){
//            queryWrapper.ge(ViewLogEntry::getCreated, condition.getCreateDate());
//        }

        return findAll(pageable, queryWrapper);
    }

}
