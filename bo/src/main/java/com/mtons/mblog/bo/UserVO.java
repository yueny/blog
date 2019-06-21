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
import com.mtons.mblog.bo.RoleVO;
import com.yueny.rapid.lang.mask.annotation.Mask;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserVO extends AbstractMaskBo implements Serializable {
	private static final long serialVersionUID = 107193816173103116L;

	private long id;
	private String username;

	@JSONField(serialize = false)
	@Mask
	private String password;

	/**
	 * 用户头像路径。如果为空，由 thumbnailCode 代替
	 */
	@Deprecated
	private String avatar;
	/**
	 * 上传的头像编号， 取自mto_resource表
	 */
	private String thumbnailCode;

	private String name;

	/**
	 * 自定义个性域名
	 */
	private String domainHack;
	/**
	 * 用户唯一标示
	 */
	private String uid;

	private String email;

	private int posts; // 文章数
	private int comments; // 发布评论数

	private Date created;
	private Date lastLogin;
	private String signature; // 个性签名

	private int status;

	/**
	 * 上传的头像信息， 取自mto_resource表
	 */
	private ResourceVO thumbnailVo;

	@JSONField(serialize = false)
	private List<RoleVO> roles = new ArrayList<>();

}
