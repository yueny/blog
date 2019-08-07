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
import com.mtons.mblog.dao.mapper.ViewLogMapper;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
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
    public List<ViewLogVO> findListByIp(String ip) {
        LambdaQueryWrapper<ViewLogEntry> queryWrapper = new QueryWrapper<ViewLogEntry>().lambda();
        queryWrapper.eq(ViewLogEntry::getClientIp, ip);

        List<ViewLogEntry> list = list(queryWrapper);

        if(CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        return map(list, ViewLogVO.class);
    }

}
