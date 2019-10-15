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

import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置实体
 */
@Getter
@Setter
public class OptionsBo extends AbstractIDBo {
	/**
	 * 类型。 0为有效
	 */
	private int type;

	/**
	 * 配置的键
	 */
	private String key;

	/**
	 * 配置的值
	 */
	private String value;

}
