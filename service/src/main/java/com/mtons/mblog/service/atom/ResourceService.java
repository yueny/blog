/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.entity.Resource;

import java.util.List;

/**
 * 图片资源管理
 */
public interface ResourceService extends IService<Resource> {
	ResourceBO findByMd5(String md5);

	ResourceBO findByThumbnailCode(String thumbnailCode);

	List<ResourceBO> findByMd5In(List<String> md5);

	List<ResourceBO> find0Before(String time);

//	int updateAmount(Collection<String> md5s, long increment);
//
//	int intupdateAmountByIds(Collection<Long> ids, long increment);
}
