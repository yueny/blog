package com.mtons.mblog.service.comp.configure.impl;

import com.mtons.mblog.service.comp.configure.IConfigureConstant;
import com.mtons.mblog.service.comp.configure.IConfigureGetService;
import com.mtons.mblog.service.comp.configure.ISiteConfigService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 站点服务配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
@Component
public class SiteConfigService implements ISiteConfigService {
	@Autowired
	private IConfigureGetService configureGetService;

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
	public String getSiteTalker() {
		siteTalker = configureGetService.getKey(IConfigureConstant.SITE_TALKER_KEY);
		if(StringUtils.isEmpty(siteTalker)){
			return "星光不问赶路人，时光不负有心人";
		}

		return encode(siteTalker);
	}

	@Override
	public boolean isShowLocker() {
		showLocker = configureGetService.getBoolean(IConfigureConstant.SITE_SHOW_LOCKER_KEY);

		return showLocker;
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

}
