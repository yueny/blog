/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yueny.rapid.lang.common.enums.GenderType;
import com.yueny.rapid.lang.mask.annotation.Mask;
import com.yueny.rapid.lang.mask.annotation.Maskble;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserBO extends AbstractMaskBo implements Serializable {
	private static final long serialVersionUID = 107193816173103116L;

	private long id;

	/** 表创建时间 */
	private Date created;

	/** 表修改时间 */
	private Date updated;

	/**
	 * 用户唯一标示
	 */
	private String uid;

	private String username;

	/**
	 * 昵称
	 */
	private String name;

	/**
	 * 用户头像路径。如果为空，由 thumbnailCode 代替
	 */
	@Deprecated
	private String avatar;
	/**
	 * 上传的头像编号， 取自mto_resource表
	 */
	private String thumbnailCode;

	private String email;

	@JSONField(serialize = false)
	@Maskble
	private String password;

	/**
	 * 0 激活， 1禁用
	 */
	private int status;

	private Date lastLogin;

	/**
	 * 性别, 1男， 2女， 0未知
	 */
	private GenderType gender;

	/**
	 * 我的发布文章数
	 */
	private int posts; // 文章数
	/**
	 * 我的发布评论数
	 */
	private int comments; // 发布评论数

	private String signature; // 个性签名

	/**
	 * 自定义个性域名
	 */
	private String domainHack;

}
