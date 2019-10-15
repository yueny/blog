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
import com.mtons.mblog.bo.OptionsBo;
import com.mtons.mblog.dao.mapper.OptionsMapper;
import com.mtons.mblog.entity.bao.Options;
import com.mtons.mblog.service.atom.bao.OptionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *
 */
@Service
public class OptionsServiceImpl extends AbstractPlusService<OptionsBo, Options, OptionsMapper>
		implements OptionsService {
	@Override
	public OptionsBo findByKey(String key) {
		LambdaQueryWrapper<Options> queryWrapper = new QueryWrapper<Options>().lambda();
		queryWrapper.eq(Options::getKey, key);

		return find(queryWrapper);
	}

	@Override
	@Transactional
	public void update(Map<String, String> options) {
		if (options == null) {
			return;
		}

		options.forEach((key, value) -> {
			OptionsBo optionsBo = findByKey(key);

			String val = StringUtils.trim(value);
			if (optionsBo != null) {
				// 如果值在数据库中已存在， 则只更新值
				optionsBo.setValue(val);
			} else {
				optionsBo = new OptionsBo();
				optionsBo.setKey(key);
				optionsBo.setValue(val);
			}
			// 更新数据库储存的 option 配置信息
			saveOrUpdate(optionsBo);
		});
	}

}
