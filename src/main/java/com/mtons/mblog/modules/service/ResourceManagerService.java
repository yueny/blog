/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.ResourceVO;

import java.util.Collection;

/**
 * 图片资源管理
 */
public interface ResourceManagerService {
	int updateAmount(Collection<String> md5s, long increment);

	int updateAmountByIds(Collection<Long> ids, long increment);

	/**
	 *
	 * 保存图片资源
	 *
	 * @param resourceVO
	 * @return 图片资源编号
	 */
	String save(ResourceVO resourceVO);
}
