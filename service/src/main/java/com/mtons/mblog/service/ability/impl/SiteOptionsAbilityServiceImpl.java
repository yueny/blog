package com.mtons.mblog.service.ability.impl;

import com.mtons.mblog.base.consts.options.OptionsKeysConsts;
import com.mtons.mblog.bo.OptionsBo;
import com.mtons.mblog.service.atom.bao.OptionsService;
import com.mtons.mblog.service.core.ConfigKeysConstant;
import com.mtons.mblog.service.ability.IConfigAbilityService;
import com.mtons.mblog.service.ability.ISiteOptionsAbilityService;
import com.mtons.mblog.service.configuration.site.SiteOptionsConfiguration;
import com.mtons.mblog.service.storage.NailPathData;
import com.mtons.mblog.service.util.file.FilePathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 站点服务配置，取自配置中心或者表 options
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
@Component
public class SiteOptionsAbilityServiceImpl implements ISiteOptionsAbilityService, InitializingBean {
	@Autowired
	private IConfigAbilityService configAbilityService;

	@Autowired
	private OptionsService optionsService;
	@Autowired
	private SiteOptionsConfiguration siteOptionsConfiguration;

	private static final String image_server_uri   = OptionsKeysConsts.IMAGE_SERVER_URI;
	private static final String image_server_location = OptionsKeysConsts.IMAGE_SERVER_LOCATION;

	/**
	 * 每日箴言， 通过@Value的形式不能实时获取
	 */
//	@Getter
//	@Setter
//	@Value("${"+ IConfigureConstant.SITE_TALKER_KEY +"}")
	private String siteTalker;

	/**
	 * 是否显示时钟
	 */
	private boolean showLocker;

	@Override
	public SystemGetVo getSystemGetVo() {
		return SystemGetVo.builder()
				.siteTalker(getSiteTalker())
				.showLocker(isShowLocker())
				.build();
	}

	@Override
	public ImageLocationVo getImageLocationVo() {
		return ImageLocationVo.builder()
				.location(getLocation(image_server_location, siteOptionsConfiguration.getLocation()))
				.locationUri(getLocation(image_server_uri, ""))
				.build();
	}

	@Override
	public ImageLocationVo getNativeLocationVo() {
		return ImageLocationVo.builder()
				/* 先查询数据库配置，不存在则查询默认配置文件 */
				.location(getLocation(OptionsKeysConsts.NATIVE_SERVER_LOCATION, siteOptionsConfiguration.getLocation()))
				.locationUri(getLocation(OptionsKeysConsts.NATIVE_SERVER_URI, siteOptionsConfiguration.getLocationUri()))
				.build();
	}


	@Override
	public String computeWholePathName(NailPathData nailPath, String md5) {
		return FilePathUtils.wholePathName(nailPath.get(), nailPath.getOriginalFilename(), md5);
	}

	@Override
	public String getValue(String key) {
		String val = siteOptionsConfiguration.getValue(key);

		if(StringUtils.isEmpty(val)){
			OptionsBo optionsBo = optionsService.findByKey(key);

			if(optionsBo == null || StringUtils.isEmpty(optionsBo.getValue())){
				val = "";
			}else{
				val = optionsBo.getValue();
			}
		}

		return val;
	}

	@Override
	public Integer getValueInteger(String key) {
		return Integer.parseInt(getValue(key));
	}

	@Override
	public Integer[] getValueIntegerArray(String key, String separator) {
		//@NotNull
		String value = getValue(key);

		String[] array = value.split(separator);
		Integer[] ret = new Integer[array.length];
		for (int i = 0; i < array.length; i ++) {
			ret[i] = Integer.parseInt(array[i]);
		}
		return ret;
	}

	/**
	 * 获取每日箴言
	 */
	private String getSiteTalker() {
		siteTalker = configAbilityService.getKey(ConfigKeysConstant.SITE_TALKER_KEY);
		if(StringUtils.isEmpty(siteTalker)){
			return "星光不问赶路人，时光不负有心人";
		}

		return encode(siteTalker);
	}

	/**
	 * 是否显示时钟
	 */
	private boolean isShowLocker() {
		showLocker = configAbilityService.getBoolean(ConfigKeysConstant.SITE_SHOW_LOCKER_KEY);

		return showLocker;
	}

	/**
	 * 先查询数据库配置，不存在则查询默认配置文件
	 *
	 */
	private String getLocation(String optionsKey, String defaultVal) {
		OptionsBo optionsBo = optionsService.findByKey(optionsKey);
		if(optionsBo == null || StringUtils.isEmpty(optionsBo.getValue())){
			// 配置项不存在，则返回本地配置
			return defaultVal;
		}

		return optionsBo.getValue().trim();
	}

	private Integer getLocationInteger(String optionsKey, String defaultVal) {
		return Integer.parseInt(getLocation(optionsKey, defaultVal));
	}

	/**
	 * 编码集转换
	 *
	 * @param val 值
	 * @return
	 */
	private String encode(String val){
		try{
			// 乱码的特殊处理
			//判断是不是ISO-8859-1
			if(val.equals(new String(val.getBytes("iso8859-1"), "iso8859-1"))) {
				val = new String(val.getBytes("iso-8859-1"), "UTF-8");
			}
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return val;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//.
	}
}
