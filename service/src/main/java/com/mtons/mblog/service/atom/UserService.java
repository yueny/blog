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

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.AccountProfile;
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
public interface UserService {
	/**
	 * 分页查询
	 * @param pageable
	 * @param name
	 */
	Page<UserBO> paging(Pageable pageable, String name);

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

	/**
	 * 注册
	 * @param user
	 */
	UserBO register(UserBO user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@CacheEvict(key = "#user.getId()")
	AccountProfile update(UserBO user);

	/**
	 * 修改用户信息
	 * @param email
	 * @return
	 */
	@CacheEvict(key = "#id")
	AccountProfile updateEmail(long id, String email);

	/**
	 * 查询单个用户
	 * @param userId
	 * @return
	 */
	@Cacheable(key = "#userId")
    UserBO get(long userId);

	/**
	 * 查询单个用户
	 * @param uid
	 * @return
	 */
	@Cacheable(key = "#uid")
	UserBO get(String uid);

	UserBO getByUsername(String username);

	UserBO getByEmail(String email);

	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	@CacheEvict(key = "#id")
	AccountProfile updateAvatar(long id, String path);

	/**
	 * 修改密码
	 * @param uid
	 * @param newPassword 新密码，输入明文
	 */
	boolean updatePassword(String uid, String newPassword) throws InvalidException;

	/**
	 * 修改密码
	 * @param uid
	 * @param oldPassword  旧密码，输入明文
	 * @param newPassword  新密码，输入明文
	 */
	boolean updatePassword(String uid, String oldPassword, String newPassword) throws InvalidException;

	/**
	 * 修改用户状态
	 * @param id
	 * @param status
	 */
	boolean updateStatus(long id, int status);

	long count();

	/**
	 * 查询个性化域名的所有人信息. 不存在则返回null
	 */
	UserBO getByDomainHack(String domainHack);

}
