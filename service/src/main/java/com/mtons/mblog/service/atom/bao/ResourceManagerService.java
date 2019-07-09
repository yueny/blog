/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.entity.bao.Resource;

import java.util.Collection;

/**
 * 图片资源管理
 */
public interface ResourceManagerService extends IService<Resource> {
	int updateAmount(Collection<String> md5s, long increment);

	int updateAmountByIds(Collection<Long> ids, long increment);

	/**
	 *
	 * 保存图片资源
	 *
	 * @param resourceBO
	 * @return 图片资源编号
	 */
	String save(ResourceBO resourceBO);
}
