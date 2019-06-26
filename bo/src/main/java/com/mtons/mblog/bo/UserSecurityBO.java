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

import com.mtons.mblog.base.enums.NeedChangeType;
import com.yueny.rapid.lang.mask.annotation.Maskble;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户安全配置表
 */
@Getter
@Setter
public class UserSecurityBO extends AbstractMaskBo implements Serializable {


	private long id;
	private String username;

	/** 表创建时间 */
	private Date created;

	/** 表修改时间 */
	private Date updated;

	/**
	 * 密码盐值
	 */
	@Maskble
	private String salt;

	/**
	 * 是否需要修改密码， 0为不需要，1为需要修改密码
	 */
	private NeedChangeType needChangePw;

}
