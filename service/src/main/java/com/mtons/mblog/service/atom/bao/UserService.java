/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.bao.User;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Set;

/**
 *
 * @author yueny09 <deep_blue_yang@126.com>
 *
 * @DATE 2019/8/14 20:05
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface UserService extends IPlusBizService<UserBO, User> {
//	/**
//	 * 登录
//	 * @param username
//	 * @param password
//	 * @return
//	 */
//	AccountProfile login(String username, String password);
//
//	/**
//	 * 登录,用于记住登录时获取用户信息
//	 * @param id
//	 * @return
//	 */
//	AccountProfile findProfile(Long id);

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
	boolean update(UserBO user);

	/**
	 * 修改用户信息
	 * @param email
	 * @return
	 */
	@CacheEvict(key = "#id")
	boolean updateEmail(long id, String email);

	//	/**
//	 * 查询单个用户
//	 * @param userId
//	 * @return
//	 */
//	@Cacheable(key = "#userId")
//    UserBO get(long userId);

	/**
	 * 查询单个用户
	 * @param uid
	 * @return
	 */
	@Cacheable(key = "#uid")
	UserBO find(String uid);

	UserBO findByUsername(String username);

	UserBO findByEmail(String email);

	/**
	 * 查询个性化域名的所有人信息. 不存在则返回null
	 */
	UserBO findByDomainHack(String domainHack);

	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	@CacheEvict(key = "#id")
	boolean updateAvatar(long id, String path);

	/**
	 * 修改密码
	 * @param uid
	 * @param newPassword 新密码，输入明文
	 */
	boolean updatePassword(String uid, String newPassword) throws InvalidException;

	/**
	 * 修改用户状态
	 * @param uid
	 * @param status
	 */
	boolean updateStatus(String uid, StatusType status);

	/**
	 * 自增发布文章数
	 * @param uid
	 */
	void identityPost(String uid, boolean plus);

	/**
	 * 自增评论数
	 * @param uid
	 */
	void identityComment(String uid, boolean plus);

	/**
	 * 批量自增评论数
	 * @param uids
	 * @param plus
	 */
	void identityComment(Set<String> uids, boolean plus);

}
