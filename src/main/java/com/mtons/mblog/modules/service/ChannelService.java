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

import com.mtons.mblog.modules.data.ChannelTreeVO;
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
	/**
	 * 查询指定状态全部渠道清单
	 */
	List<ChannelVO> findAll(int status);

	/**
	 * 查询指定状态的根渠道清单， parentChannelCode 为 -1
	 */
	List<ChannelVO> findAllByRoot(int status);

	/**
	 * 查询指定状态的根渠道清单树， parentChannelCode 为 -1
	 */
	List<ChannelTreeVO> findAllByRootForTree(int status);

	/**
	 * 查询指定状态、指定父渠道编号的的渠道清单
	 */
	List<ChannelVO> findAll(int status, String parentChannelCode);

	/**
	 * 查询指定状态、指定父渠道编号的的渠道清单树
	 */
	List<ChannelTreeVO> findAllForTree(int status, String parentChannelCode);

	Map<Integer, ChannelVO> findMapByIds(Collection<Integer> ids);
	ChannelVO getById(int id);

	ChannelVO getByChannelCode(String channelCode);

	void update(ChannelVO channel);
	void updateWeight(int id, int weighted);
	void delete(int id);
	long count();

	ChannelVO getByFlag(String flag);
	Map<String, ChannelVO> findMapByFlags(Collection<String> flags);
}
