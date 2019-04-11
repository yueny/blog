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

import com.mtons.mblog.modules.data.ChannelVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 栏目管理
 * 
 * @author langhsu
 *
 */
public interface ChannelService {
	List<ChannelVO> findAll(int status);
	Map<Integer, ChannelVO> findMapByIds(Collection<Integer> ids);
	ChannelVO getById(int id);
	void update(ChannelVO channel);
	void updateWeight(int id, int weighted);
	void delete(int id);
	long count();

	ChannelVO getByFlag(String flag);
	Map<String, ChannelVO> findMapByFlags(Collection<String> flags);
}
