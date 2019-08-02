/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 * Authors:
 *   leiwen <chrisredfield1985@126.com> , boyan <killme2008@gmail.com>
 */
package com.mtons.mblog.service.comp.base.impl;

import com.mtons.mblog.service.comp.base.IPasswdService;
import com.yueny.superclub.util.crypt.core.PBECoder;
import com.yueny.superclub.util.crypt.core.group.MD5Util;
import org.springframework.stereotype.Service;


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
	/**
	 * 获取合法的盐值
	 */
	@Override
	public String getSalt() {
		return PBECoder.initSalt();
	}

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
		return MD5Util.encode(passwordVal, salt);
	}

}
