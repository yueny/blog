/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.dao.mapper.DemoMapper;
import com.mtons.mblog.entity.DemoEntry;
import com.mtons.mblog.bo.DemoVO;
import com.mtons.mblog.service.atom.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DemoServiceImpl extends BaseBaoService<DemoMapper, DemoEntry> implements DemoService {
	@Autowired
	private DemoMapper demoMapper;

	@Override
	public DemoVO selectByOrderId(String orderId) {
		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
		queryWrapper.eq(DemoEntry::getAssetCode,orderId);

		DemoEntry entry = demoMapper.selectOne(queryWrapper);

		if(entry == null){
			return null;
		}

		return map(entry, DemoVO.class);
	}

	@Override
	public List<DemoVO> selectListByGuaranteeMode(Integer guaranteeMode) {
		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
		queryWrapper.eq(DemoEntry::getGuaranteeMode, guaranteeMode);

		List<DemoEntry> entrys = demoMapper.selectList(queryWrapper);

		return map(entrys, DemoVO.class);
	}
}
