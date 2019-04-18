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
import com.mtons.mblog.modules.data.ChannelTreeVO;
import com.mtons.mblog.modules.data.ChannelVO;
import com.mtons.mblog.modules.data.ResourceVO;
import com.mtons.mblog.modules.repository.ChannelRepository;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.entity.Channel;
import com.mtons.mblog.modules.service.ResourceService;
import com.yueny.rapid.lang.util.UuidUtil;
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

			// 组装 parentChannelVo
			assemblyParentChannel(po);
        });

        return list;
    }

    @Override
	public List<ChannelVO> findAllByRoot(int status) {
		return findAll(status, "-1");
	}

	@Override
	public List<ChannelTreeVO> findAllByRootForTree(int status) {
		return findAllForTree(status, "-1");
	}

	@Override
	public List<ChannelVO> findAll(int status, String parentChannelCode) {
		List<Channel> entrys;
		if (status > Consts.IGNORE) {
			entrys = channelRepository.findAllByStatusAndParentChannelCode(status, parentChannelCode, sort);
		} else {
			entrys = channelRepository.findAllByParentChannelCode(parentChannelCode, sort);
		}

		if(CollectionUtils.isEmpty(entrys)){
			return Collections.emptyList();
		}
		List<ChannelVO> list =  map(entrys, ChannelVO.class);
		list.forEach(po -> {
			assemblyChannel(po, po.getThumbnailCode());

			// 组装 parentChannelVo
			assemblyParentChannel(po);
		});

		return list;
	}

	@Override
	public List<ChannelTreeVO> findAllForTree(int status, String parentChannelCode) {
		List<ChannelVO> list = findAll(status, parentChannelCode);

		List<ChannelTreeVO> treeTmpList = new ArrayList<>();
		list.forEach(channelVO -> {
			// 组装 parentChannelVo
			assemblyParentChannel(channelVO);

			ChannelTreeVO treeVo = new ChannelTreeVO();
			BeanUtils.copyProperties(channelVO, treeVo);

			List<ChannelTreeVO> childrenList = findAllForTree(status, channelVO.getChannelCode());
			if(CollectionUtils.isNotEmpty(childrenList)){
				treeVo.setChildrenList(childrenList);
			}

			treeTmpList.add(treeVo);
		});

		return treeTmpList;
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
			// 组装 parentChannelVo
			assemblyParentChannel(po);

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
		// 组装 parentChannelVo
		assemblyParentChannel(channelVO);

		return channelVO;
	}

	@Override
	public ChannelVO getByChannelCode(String channelCode) {
		Channel channel = channelRepository.findByChannelCode(channelCode);

		if(channel == null){
			return null;
		}
		ChannelVO channelVO =  map(channel, ChannelVO.class);

		assemblyChannel(channelVO, channelVO.getThumbnailCode());
		// 组装 parentChannelVo
		assemblyParentChannel(channelVO);

		return channelVO;
	}

	@Override
	@Transactional
	public void update(ChannelVO channelVo) {
		//
		if(StringUtils.isEmpty(channelVo.getKey())){
			channelVo.setKey(UuidUtil.getUuidForString12());
		}
		if(StringUtils.isEmpty(channelVo.getChannelCode())){
			channelVo.setChannelCode(UuidUtil.getUUIDForNumber10X("C"));
		}
		if(StringUtils.isEmpty(channelVo.getParentChannelCode())){
			channelVo.setParentChannelCode("-1");
		}

		Optional<Channel> optional = channelRepository.findById(channelVo.getId());

		Channel po = optional.orElse(map(channelVo, Channel.class));

		// flag 不改变
		po.setFlag(channelVo.getFlag());
		// BeanUtils.copyProperties(channelVo, po, "flag");

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
		// 组装 parentChannelVo
		assemblyParentChannel(channelVO);

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
			// 组装 parentChannelVo
			assemblyParentChannel(po);

			rets.put(po.getFlag(), po);
		});
		return rets;
	}

	private void assemblyChannel(ChannelVO channelVO, String thumbnailCode){
		if(StringUtils.isNotEmpty(thumbnailCode)){
			ResourceVO vo = resourceService.findByThumbnailCode(thumbnailCode);

			if(vo != null){
				channelVO.setThumbnail(vo.getPath());
			}
		}
	}

	private void assemblyParentChannel(ChannelVO channelVO){
		if(StringUtils.equals(channelVO.getParentChannelCode(), "-1") ){
			channelVO.setParentChannelVo(new ChannelVO("-1", "/"));
		}else{
			ChannelVO cvo = getByChannelCode(channelVO.getParentChannelCode());
			if(cvo != null){
				channelVO.setParentChannelVo(cvo);
			}
		}
	}
}
