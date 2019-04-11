/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.ChannelVO;
import com.mtons.mblog.modules.data.ResourceVO;
import com.mtons.mblog.modules.repository.ChannelRepository;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.entity.Channel;
import com.mtons.mblog.modules.service.ResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 栏目管理
 *
 * @author langhsu
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl extends BaseService implements ChannelService {
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private ResourceService resourceService;

	private Sort sort = Sort.by(
			new Sort.Order(Sort.Direction.DESC, "weight"),
			new Sort.Order(Sort.Direction.ASC, "id")
	);
//	Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");

	@Override
	public List<ChannelVO> findAll(int status) {
		List<Channel> entrys;
		if (status > Consts.IGNORE) {
			entrys = channelRepository.findAllByStatus(status, sort);
		} else {
			entrys = channelRepository.findAll(sort);
		}

		if(CollectionUtils.isEmpty(entrys)){
			return Collections.emptyList();
		}
		List<ChannelVO> list =  map(entrys, ChannelVO.class);
		list.forEach(po -> {
			assemblyChannel(po, po.getThumbnailCode());
		});

		return list;
	}

	@Override
	public Map<Integer, ChannelVO> findMapByIds(Collection<Integer> ids) {
		List<Channel> list = channelRepository.findAllById(ids);

		if(CollectionUtils.isEmpty(list)){
			return Collections.emptyMap();
		}

		Map<Integer, ChannelVO> rets = new HashMap<>();
		map(list, ChannelVO.class).forEach(po -> {
			assemblyChannel(po, po.getThumbnailCode());

			rets.put(po.getId(), po);
		});
		return rets;
	}

	@Override
	public ChannelVO getById(int id) {
		Channel channel = channelRepository.findById(id).get();

		if(channel == null){
			return null;
		}
		ChannelVO channelVO =  map(channel, ChannelVO.class);

		assemblyChannel(channelVO, channelVO.getThumbnailCode());

		return channelVO;
	}

	@Override
	@Transactional
	public void update(ChannelVO channelVo) {
		Optional<Channel> optional = channelRepository.findById(channelVo.getId());

		Channel po = optional.orElse(new Channel());
		BeanUtils.copyProperties(channelVo, po, "flag"); // flag 不改变
		channelRepository.save(po);
	}

	@Override
	@Transactional
	public void updateWeight(int id, int weighted) {
		Channel po = channelRepository.findById(id).get();

		int max = Consts.ZERO;
		if (Consts.FEATURED_ACTIVE == weighted) {
			max = channelRepository.maxWeight() + 1;
		}
		po.setWeight(max);
		channelRepository.save(po);
	}

	@Override
	@Transactional
	public void delete(int id) {
		channelRepository.deleteById(id);
	}

	@Override
	public long count() {
		return channelRepository.count();
	}

	@Override
	public ChannelVO getByFlag(String flag) {
		Channel channel = channelRepository.findByFlag(flag);

		if(channel == null){
			return null;
		}
		ChannelVO channelVO = map(channel, ChannelVO.class);

		assemblyChannel(channelVO, channelVO.getThumbnailCode());
		return channelVO;
	}

	@Override
	public Map<String, ChannelVO> findMapByFlags(Collection<String> flags) {
		List<Channel> list = channelRepository.findAllByFlagIn(flags);

		if(CollectionUtils.isEmpty(list)){
			return Collections.emptyMap();
		}

		Map<String, ChannelVO> rets = new HashMap<>();
		map(list, ChannelVO.class).forEach(po -> {
			assemblyChannel(po, po.getThumbnailCode());

			rets.put(po.getFlag(), po);
		});
		return rets;
	}

	private void assemblyChannel(ChannelVO channelVO, String thumbnailCode){
		if(StringUtils.isNotEmpty(thumbnailCode)){
			ResourceVO vo = resourceService.findByMd5(thumbnailCode);

			if(vo != null){
				channelVO.setThumbnail(vo.getThumbnailCode());
			}
		}
	}
}
