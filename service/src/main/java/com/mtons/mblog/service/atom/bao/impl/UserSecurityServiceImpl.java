/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.UserSecurityBO;
import com.mtons.mblog.dao.mapper.UserSecurityMapper;
import com.mtons.mblog.entity.bao.UserSecurityEntry;
import com.mtons.mblog.service.atom.bao.IUserSecurityService;
import org.springframework.stereotype.Service;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午2:12
 *
 */
@Service
public class UserSecurityServiceImpl extends BaseBaoService<UserSecurityMapper, UserSecurityEntry>
		implements IUserSecurityService {
	@Override
	public boolean save(UserSecurityBO bo) {
		// 不存在数据
		if(getByUid(bo.getUid()) == null){
			int rs = baseMapper.insert(map(bo, UserSecurityEntry.class));
			return rs == 1;
		}

		// 已经存在，则更新
		UserSecurityEntry entry = new UserSecurityEntry();
		entry.setNeedChangePw(bo.getNeedChangePw());
		entry.setSalt(bo.getSalt());

		LambdaQueryWrapper<UserSecurityEntry> queryWrapper = new QueryWrapper<UserSecurityEntry>().lambda();
		queryWrapper.eq(UserSecurityEntry::getUid, bo.getUid());
		return baseMapper.update(entry, queryWrapper) == 1;
	}

	@Override
	public UserSecurityBO getByUid(String uid) {
		LambdaQueryWrapper<UserSecurityEntry> queryWrapper = new QueryWrapper<UserSecurityEntry>().lambda();
		queryWrapper.eq(UserSecurityEntry::getUid, uid);

		UserSecurityEntry entry = getOne(queryWrapper);
		if(entry == null){
			return null;
		}

		return map(entry, UserSecurityBO.class);
	}

//	@Autowired
//	private DemoMapper demoMapper;
//
//	@Override
//	public DemoVO selectByOrderId(String orderId) {
//		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
//		queryWrapper.eq(DemoEntry::getAssetCode,orderId);
//
//		DemoEntry entry = demoMapper.selectOne(queryWrapper);
//
//		if(entry == null){
//			return null;
//		}
//
//		return map(entry, DemoVO.class);
//	}
//
//	@Override
//	public List<DemoVO> selectListByGuaranteeMode(Integer guaranteeMode) {
//		LambdaQueryWrapper<DemoEntry> queryWrapper = new QueryWrapper<DemoEntry>().lambda();
//		queryWrapper.eq(DemoEntry::getGuaranteeMode, guaranteeMode);
//
//		List<DemoEntry> entrys = demoMapper.selectList(queryWrapper);
//
//		return map(entrys, DemoVO.class);
//	}
}
