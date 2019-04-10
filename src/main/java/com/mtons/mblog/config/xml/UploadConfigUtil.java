/**
 *
 */
package com.mtons.mblog.config.xml;

import com.mtons.mblog.config.ApplicationContextHolder;
import com.mtons.mblog.config.UploadConfigConfiguration;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年9月22日 下午5:22:52
 *
 */
public final class UploadConfigUtil {
	/**
	 * 获取xml配置
	 */
	public static UploadConfigModelData getUploadConfig() {
		UploadConfigConfiguration configConfiguration = ApplicationContextHolder.getBean(UploadConfigConfiguration.class);
		return configConfiguration.getConfigModelData();
	}

}
