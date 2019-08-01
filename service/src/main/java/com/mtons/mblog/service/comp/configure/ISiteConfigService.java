package com.mtons.mblog.service.comp.configure;

/**
 *
 * 站点服务配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
public interface ISiteConfigService extends IConfigureService {
	/**
	 * 获取每日箴言
	 */
	String getSiteTalker();

	/**
	 * 是否显示时钟
	 */
	boolean isShowLocker();

}
