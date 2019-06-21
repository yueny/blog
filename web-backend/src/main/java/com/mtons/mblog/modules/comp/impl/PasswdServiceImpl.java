/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 * Authors:
 *   leiwen <chrisredfield1985@126.com> , boyan <killme2008@gmail.com>
 */
package com.mtons.mblog.modules.comp.impl;

import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.utils.MD5;
import com.mtons.mblog.service.comp.IPasswdService;
import com.mtons.mblog.entity.User;
import com.mtons.mblog.modules.repository.UserRepository;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 密码服务 
 * 
 * @author yueny09 <yueny09@163.com>
 * 
 * @DATE 2018年4月6日 下午5:22:24
 *
 */
@Service
public class PasswdServiceImpl implements IPasswdService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * 密码加密，根据欲加密明文数据和盐获取密文
	 *
	 * @param passwordVal
	 *            欲加密明文数据
	 * @param salt
	 *            盐值
	 */
	@Override
	public String encode(String passwordVal, String salt) {
		// 20180606
		String val =  MD5.md5(passwordVal);
		return val;

//		return MD5Util.encode(passwordVal, salt);
	}

	@Override
	public boolean changePassword(String uid, String resetPwd) throws InvalidException {
		if (!StringUtils.hasLength(uid)) {
			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), "用户不能为空!");

			throw new InvalidException(ErrorType.INVALID_ERROR.getCode(), msg);
		}
		if (!StringUtils.hasLength(resetPwd)) {
			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), "密码不能为空!");

			throw new InvalidException(ErrorType.INVALID_ERROR.getCode(), msg);
		}

		User po = userRepository.findByUid(uid);
		if(po == null){
			throw new InvalidException(ErrorType.USER_NOT_EXIST1);
		}

		// 处理明文密码得到密文
		String pw = encode(resetPwd, "");

		//po.setPassword(pw);
		//userRepository.save(po);
		int rs = userRepository.changePassword(uid, pw);

		return rs == 1;
	}

}
