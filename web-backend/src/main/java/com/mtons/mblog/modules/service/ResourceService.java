/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package main.java.com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.ResourceVO;
import java.util.List;

/**
 * 图片资源管理
 */
public interface ResourceService {
	ResourceVO findByMd5(String md5);

	ResourceVO findByThumbnailCode(String thumbnailCode);

	List<ResourceVO> findByMd5In(List<String> md5);

	List<ResourceVO> find0Before(String time);

//	int updateAmount(Collection<String> md5s, long increment);
//
//	int intupdateAmountByIds(Collection<Long> ids, long increment);
}
