package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.bo.AttackIpBo;
import com.mtons.mblog.dao.repository.AttackIpRepository;
import com.mtons.mblog.entity.jpa.AttackIpEntry;
import com.mtons.mblog.service.atom.jpa.AttackIpService;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 攻击者IP信息
 */
@Service
public class AttackIpServiceImpl extends AbstractJpaService<AttackIpBo, AttackIpEntry, AttackIpRepository>
		implements AttackIpService {
	@Override
	public boolean saveIfAbsent(AttackIpBo attackIpBo) {
		if(attackIpBo == null){
			return false;
		}

		if(findByIp(attackIpBo.getClientIp()) != null){
			return false;
		}

		return insert(attackIpBo);
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

	/**
	 * 查询所有ip列表
	 * @return 列表
	 */
	@Override
	public Set<String> findAllIps(){
		List<AttackIpBo> list = findAll();

		if(CollectionUtil.isEmpty(list)){
			return Collections.emptySet();
		}

		return list.stream().map(AttackIpBo::getClientIp).collect(Collectors.toSet());
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
