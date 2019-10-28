package com.mtons.mblog.service.comp.configure;

import com.mtons.mblog.service.storage.NailPathData;

/**
 *
 * 站点服务配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
public interface ISiteConfigService {
	/**
	 * 获取每日箴言
	 */
	String getSiteTalker();

	/**
	 * 是否显示时钟
	 */
	boolean isShowLocker();

	/**
	 * 获取图片存储的相对路径 ${image_server_location}
	 *
	 * @return
	 */
	String getLocation();

	/**
	 * 获取图片存储的网络地址
	 *
	 * @return
	 */
	String getLocationUri();

	/**
	 * 获取图片存储的网络地址
	 *
	 * @param nailPath
	 * @param md5
	 * @return
	 */
	String getWholePathName(NailPathData nailPath, String md5);

	/**
	 * 获取配置的值
	 *
	 * @param key
	 * @return
	 */
	String getValue(String key);

	Integer getIntegerValue(String key);

	Integer[] getIntegerArrayValue(String key, String separator);
}
