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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.dao.mapper.DemoMapper;
import com.mtons.mblog.entity.bao.DemoEntry;
import com.mtons.mblog.bo.DemoBo;
import com.mtons.mblog.service.atom.bao.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午2:12
 *
 */
@Service
public class DemoServiceImpl extends AbstractPlusService<DemoBo, DemoEntry, DemoMapper> implements DemoService {
	@Override
	public DemoBo selectByOrderId(String orderId) {
		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
		queryWrapper.eq(DemoEntry::getAssetCode,orderId);

		DemoEntry entry = baseMapper.selectOne(queryWrapper);

		if(entry == null){
			return null;
		}

		return map(entry, DemoBo.class);
	}

	@Override
	public List<DemoBo> selectListByGuaranteeMode(Integer guaranteeMode) {
		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
		queryWrapper.eq(DemoEntry::getGuaranteeMode, guaranteeMode);

		return findAll(queryWrapper);
	}

}
