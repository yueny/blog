/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.model;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteOptionsControlsVO extends AbstractMaskBo {
//	@Mask
	/**
	 * 注册开关
	 */
	private boolean register;
	/**
	 * 发布文章开关
	 */
	private boolean post;

	/**
	 * 评论开关, true 为允许评论
	 */
	private boolean comment;

	/**
	 * 是否显示注册按钮(登陆开关是否显示)
	 */
	private boolean login_show;

	/**
	 * 是否允许匿名评论开关, true 为允许匿名评论
	 */
	private boolean commentAllowAnonymous;

	/**
	 * 注册开启邮箱验证
	 */
	private boolean register_email_validate;

}
