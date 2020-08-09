/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.mtons.mblog.base.enums.NeedChangeType;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.UserBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
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

	/**
	 * 是否需要修改密码， 0为不需要，1为需要修改密码
	 */
	private NeedChangeType needChangePw;

	@JSONField(serialize = false)
	private List<RolePermissionVO> roles = new ArrayList<>();

}
