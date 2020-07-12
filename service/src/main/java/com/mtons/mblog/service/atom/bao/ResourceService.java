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

import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.entity.bao.Resource;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;

import java.util.List;

/**
 * 图片资源管理
 */
public interface ResourceService extends IPlusBizService<ResourceBO, Resource> {
	ResourceBO findByMd5(String md5);

	ResourceBO findByThumbnailCode(String thumbnailCode);

	List<ResourceBO> findByMd5In(List<String> md5);

	List<ResourceBO> find0Before(String time);

//	int updateAmount(Collection<String> md5s, long increment);
//
//	int intupdateAmountByIds(Collection<Long> ids, long increment);
}
