package com.mtons.mblog.service.comp.configure;

import com.mtons.mblog.service.storage.NailPathData;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
	 * 获取配置中心的配置信息
	 */
	SystemGetVo getSystemGetVo();

	/**
	 * 获取 Image 模式下的图片存储的信息
	 */
	ImageLocationVo getImageLocationVo();

	/**
	 * 获取 Native 本地模式下的图片存储的信息
	 */
	ImageLocationVo getNativeLocationVo();

	/**
	 * 获取图片存储的网络地址
	 *
	 * @param nailPath
	 * @param md5
	 * @return
	 */
	String computeWholePathName(NailPathData nailPath, String md5);

	/**
	 * 获取配置的值， 先取 site, 再取 options
	 *
	 * @param key
	 * @return
	 */
	String getValue(String key);

	/**
	 * 获取配置的值， 先取 site, 再取 options
	 *
	 * @param key
	 * @return
	 */
	Integer getValueInteger(String key);

	/**
	 * 获取配置的值， 先取 site, 再取 options
	 *
	 * @param key
	 * @return
	 */
	Integer[] getValueIntegerArray(String key, String separator);



	/**
	 * 配置中心的配置信息获取
	 */
	@Getter
	@Setter
	@Builder
	class SystemGetVo extends AbstractMaskBo {
		/**
		 * 获取每日箴言
		 */
		private String siteTalker;

		/**
		 * 是否显示时钟
		 */
		private boolean showLocker;
	}

	/**
	 * 图片存储的信息
	 */
	@Getter
	@Setter
	@Builder
	class ImageLocationVo extends AbstractMaskBo {

		/**
		 * 本地/图片服务器根目录 ${image_server_location}
		 *
		 * @return
		 */
		private String location;

		/**
		 * 获取图片存储的网络地址/图片服务器uri ${image_server_uri}
		 *
		 * @return
		 */
		private String locationUri;
//
//		/**
//		 * 附加本地存储方式 ${image_server_force_local}
//		 *
//		 * @return
//		 */
//		private Integer imageServerForceLocal;
	}
}
