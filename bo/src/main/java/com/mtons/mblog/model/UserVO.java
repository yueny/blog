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

import com.alibaba.fastjson.annotation.JSONField;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.RoleVO;
import com.mtons.mblog.bo.UserBO;
import com.yueny.rapid.lang.mask.annotation.Mask;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户业务信息
 */
@Getter
@Setter
public class UserVO extends UserBO implements Serializable {

	/**
	 * 上传的头像信息， 取自mto_resource表
	 */
	private ResourceBO thumbnail;

	@JSONField(serialize = false)
	private List<RoleVO> roles = new ArrayList<>();

}
