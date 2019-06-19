/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.comp;

import com.mtons.mblog.modules.data.SiteOptionsControlsVO;

/**
 * 开关控制配置获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午8:44
 *
 */
public interface ISiteOptionsControlsService {
	/**
	 * 开关控制对象
	 */
	SiteOptionsControlsVO getControls();

}
