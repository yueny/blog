package com.mtons.mblog.service.comp.auth;

import com.yueny.rapid.lang.exception.invalid.InvalidException;

/**
 * 用户密码认证服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月30日 下午4:49:29
 *
 */
public interface IUserPassportService {
	/**
	 * 当前用户名和密码一致性校验
	 *
	 * @param uid
	 *            登录用户uid
	 * @param password
	 *            输入的明文登录密码， not  cryptPassword
	 * @return 是否存在用户
	 */
	boolean getMatch(final String uid, final String password) throws InvalidException;

	/**
	 * 更改用户加密后的密码， 不含事务
	 *
	 * @param uid
	 *            用户uid
	 * @param oldPassport
	 *            旧明文密码
	 * @param oldPassport
	 *            表单明文密码
	 * @return 是否更新成功
	 */
	boolean modifyPassPort(final String uid, final String oldPassport, final String resetPwd) throws InvalidException;

	/**
	 * 非校验式的更改密码， 不含事务
	 *
	 * @param uid
	 *            用户uid
	 * @param resetPwd
	 *            欲重置密码的明文
	 *
	 */
	boolean changePassword(String uid, String resetPwd) throws InvalidException;

}
