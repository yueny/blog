package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.bo.AttackIpBo;
import com.mtons.mblog.dao.repository.AttackIpRepository;
import com.mtons.mblog.entity.jpa.AttackIpEntry;
import com.mtons.mblog.service.atom.jpa.AttackIpService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 攻击者IP信息
 */
@Service
public class AttackIpServiceImpl extends BaseBizService<AttackIpBo, AttackIpEntry, AttackIpRepository>
		implements AttackIpService {
	@Override
	public boolean saveIfAbsent(AttackIpBo attackIpBo) {
		if(attackIpBo == null){
			return false;
		}

		if(findByIp(attackIpBo.getClientIp()) != null){
			return false;
		}

		return save(attackIpBo);
	}

	// TODO   cache
	@Override
	public AttackIpBo findByIp(String ip) {
		/**
		 * ExampleMatcher matcher = ExampleMatcher.matching()
		 * .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
		 * .withMatcher("address" ,ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
		 * .withIgnorePaths("password");
		 * //忽略字段，即不管password是什么值都不加入查询条件
		 *
		 * Example<User> example = Example.of(user ,matcher);
		 */
		AttackIpEntry condition = new AttackIpEntry();
		condition.setClientIp(ip);
		Example<AttackIpEntry> example = Example.of(condition);

		return findOne(example);
	}

	// TODO   cache
	@Override
	public boolean deleteByIp(String ip) {
		AttackIpBo attackIpBo = findByIp(ip);

		if(attackIpBo == null){
			return false;
		}

		return delete(attackIpBo.getId());
	}

}
