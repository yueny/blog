package com.mtons.mblog.service.comp.configure;

import com.mtons.mblog.service.comp.storage.NailPathData;

/**
 *
 * 图片相关的配置
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 20:02
 */
public interface IStorageService extends IConfigureService {
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
}
