/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.data;

import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 渠道信息实体
 */
public class ChannelVO extends AbstractBo implements IBo {

	@Getter
	@Setter
	private int id;

	/**
	 * 名称
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * 对外释义标识
	 */
	@Getter
	@Setter
	private String flag;

	/**
	 * 唯一关键字
	 */
	@Getter
	@Setter
	private String key;

	@Getter
	@Setter
	private int status;

	/**
	 * 排序值
	 */
	@Getter
	@Setter
	private int weight;

	/**
	 * 预览图路径
	 */
	@Getter
	@Setter
	private String thumbnail;
	/**
	 * 图片资源编号， 取自 Resource 表
	 */
	@Getter
	@Setter
	private String thumbnailCode;

}
