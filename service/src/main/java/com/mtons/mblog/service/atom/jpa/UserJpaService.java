/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.jpa;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface UserJpaService {
	/**
	 * 分页查询
	 * @param pageable
	 * @param name
	 */
	Page<UserBO> paging(Pageable pageable, String name);


	/**
	 * 根据用户主键列表查询用户信息列表
	 * @param ids
	 * @return
	 */
	Map<Long, UserBO> findMapByIds(Set<Long> ids);

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	AccountProfile login(String username, String password);

	/**
	 * 登录,用于记住登录时获取用户信息
	 * @param id
	 * @return
	 */
	AccountProfile findProfile(Long id);


}
