/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.comp.impl;

import com.mtons.mblog.service.config.SiteOptions;
import com.mtons.mblog.modules.comp.ISiteOptionsGetService;
import com.mtons.mblog.model.SiteOptionsControlsVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配置获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午8:54
 *
 */
@Component
public class SiteOptionsGetServiceImpl implements ISiteOptionsGetService {
	@Autowired
	private SiteOptions siteOptions;
	/** */
	@Autowired
	private Mapper mapper;

	@Override
	public SiteOptionsControlsVO getControls() {
		SiteOptions.Controls controls = siteOptions.getControls();

		return mapper.map(controls, SiteOptionsControlsVO.class);
	}

	@Override
	public String getVersion() {
		return siteOptions.getVersion();
	}
}
