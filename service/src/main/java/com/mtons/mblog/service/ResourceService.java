/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtons.mblog.bo.ResourceVO;
import com.mtons.mblog.entity.Resource;

import java.util.List;

/**
 * 图片资源管理
 */
public interface ResourceService extends IService<Resource> {
	ResourceVO findByMd5(String md5);

	ResourceVO findByThumbnailCode(String thumbnailCode);

	List<ResourceVO> findByMd5In(List<String> md5);

	List<ResourceVO> find0Before(String time);

//	int updateAmount(Collection<String> md5s, long increment);
//
//	int intupdateAmountByIds(Collection<Long> ids, long increment);
}
