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

import com.mtons.mblog.model.ChannelTreeVO;
import com.mtons.mblog.bo.ChannelVO;

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
	 * 查询指定状态的根渠道编号的渠道清单， parentChannelCode 为 -1
	 */
	List<ChannelVO> findRootAll(int status);

	/**
	 * 查询指定状态的根渠道编号的渠道清单树列表， parentChannelCode 为 -1
	 */
	List<ChannelTreeVO> findRootAllForTree(int status);

	/**
	 * 查询指定状态查询全部渠道清单
	 */
	List<ChannelVO> findAll(int status);

	/**
	 * 查询指定状态、指定父渠道编号的的渠道清单
	 */
	List<ChannelVO> findAll(int status, String parentChannelCode);

	/**
	 * 查询指定状态的、且父渠道编号直接或间接为parentChannelCode 的渠道清单树列表
	 */
	List<ChannelTreeVO> findAllForTree(int status, String parentChannelCode);

	Map<Integer, ChannelVO> findMapByIds(Collection<Integer> ids);
	ChannelVO getById(Integer id);

	ChannelVO getByChannelCode(String channelCode);

	void update(ChannelVO channel);
	void updateWeight(int id, int weighted);
	void delete(String channelCode);
	long count();

	ChannelVO getByFlag(String flag);
	Map<String, ChannelVO> findMapByFlags(Collection<String> flags);
}
