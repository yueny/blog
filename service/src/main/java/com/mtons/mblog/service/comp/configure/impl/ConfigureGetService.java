package com.mtons.mblog.service.comp.configure.impl;

import com.google.common.base.Splitter;
import com.mtons.mblog.service.comp.configure.IConfigureConstant;
import com.mtons.mblog.service.comp.configure.IConfigureGetService;
import com.taobao.diamond.extend.DynamicProperties;
import com.yueny.superclub.api.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 配置中心配置动态获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
@Component
public class ConfigureGetService implements IConfigureGetService {

	@Override
	public String getKey(String key) {
		return get(key);
	}

	@Override
	public Integer getKeyInt(String key) {
		String val = get(key);
		return Integer.parseInt(val);
	}

	@Override
	public Long getKeyLong(String key) {
		String val = get(key);
		return Long.parseLong(val);
	}

	@Override
	public boolean getBoolean(String key) {
		String val = get(key);
		return Boolean.valueOf(val);
	}

	/**
	 * 动态获取配置值
	 *
	 * @param key 配置键
	 * @return 配置值
	 */
	public static String get(String key) {
		String val = DynamicProperties.staticProperties.getProperty(key);

		return val;
	}

	/**
	 * [定制化接口] 获取白名单配置值
	 *
	 * @return
	 */
	// TODO  cache
	public static Set<String> getSecurityIpWhite() {
		String val = get(IConfigureConstant.SECURITY_IP_WHITE_KEY);
		if(StringUtils.isEmpty(val)){
			return Collections.emptySet();
		}

		Set<String> ips = new HashSet<>();
		Splitter.on(Constants.PIPE).split(val).forEach(ipVal->{
			ipVal = StringUtils.trim(ipVal);
			if(StringUtils.isNotEmpty(ipVal)){
				ips.add(ipVal);
			}
		});
		return ips;
	}

	/**
	 * [定制化接口] 获取 攻击行为地址标签， 如 robots | .git | .env | .php
	 *
	 * @return
	 */
	// TODO  cache
	public static Set<String> getSecurityAttackUrl() {
		String val = get(IConfigureConstant.SECURITY_ATTACK_URL_KEY);
		if(StringUtils.isEmpty(val)){
			return Collections.emptySet();
		}

		Set<String> attackUrl = new HashSet<>();
		Splitter.on(Constants.PIPE).split(val).forEach(attackVal->{
			attackVal = StringUtils.trim(attackVal);
			if(StringUtils.isNotEmpty(attackVal)){
				attackUrl.add(attackVal);
			}
		});
		return attackUrl;
	}
}
