/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.jpa.Channel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author langhsu
 *
 */
public interface ChannelRepository extends JpaRepository<Channel, Integer>, JpaSpecificationExecutor<Channel> {
	/**
	 * 根据状态查询列表信息
	 */
	List<Channel> findAllByStatus(int status, Sort sort);

	/**
	 * 根据状态和父编号查询列表信息
	 */
	List<Channel> findAllByStatusAndParentChannelCode(int status, String parentChannelCode, Sort sort);

	/**
	 * 根据父编号查询列表信息
	 */
	List<Channel> findAllByParentChannelCode(String parentChannelCode, Sort sort);

	/**
	 * 根据 flag 列表查询列表信息
	 */
	List<Channel> findAllByFlagIn(Collection<String> flag);

	@Query("select coalesce(max(weight), 0) from Channel")
	int maxWeight();

	/**
	 * 根据 flag 查询信息
	 */
	Channel findByFlag(String flag);

	/**
	 * 根据编号查询信息
	 */
	Channel findByChannelCode(String channelCode);

	void deleteByChannelCode(String channelCode);
}
