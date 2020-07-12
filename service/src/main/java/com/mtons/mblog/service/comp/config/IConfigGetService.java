package com.mtons.mblog.service.comp.config;

/**
 * 配置中心配置动态获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
public interface IConfigGetService {
	/**
	 * 获取key键的配置值
	 *
	 * @return
	 */
	String getKey(String key);

	/**
	 * 获取key键的配置值
	 *
	 * @return
	 */
	Integer getKeyInt(String key);

	/**
	 * 获取key键的配置值
	 *
	 * @return
	 */
	Long getKeyLong(String key);

	/**
	 * 获取key键的配置值
	 *
	 * @return
	 */
	boolean getBoolean(String key);

}
