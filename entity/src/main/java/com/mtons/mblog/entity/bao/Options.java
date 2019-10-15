/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置
 * @author langhsu
 *
 */
@TableName("mto_options")
@Getter
@Setter
public class Options extends AbstractIDPlusEntry {
	/**
	 * 类型。 0为有效
	 */
	private int type;

	/**
	 * 配置的键
	 */
	@TableField(value = "key_")
	private String key;

	/**
	 * 配置的值
	 */
	private String value;
}
