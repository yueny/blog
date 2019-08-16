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
import com.mtons.mblog.base.enums.ChannelNodeType;
import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.*;

import java.util.Date;

/**
 * 访问记录
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewLogVO extends AbstractBo implements IBo {
	/**
	 * 数据库主键
	 */
	private Long id;

	/**
	 * 客户端ip
	 */
	private String clientIp;
	/**
	 * 客户端 agent
	 */
	private String clientAgent;

	/**
	 * 请求方式(get/post)
	 */
	private String method;

	/**
	 * 请求参数， json串
	 */
	private String parameterJson;

	/**
	 * 访问资源路径
	 */
	private String resourcePath;
	/**
	 * 访问资源路径描述
	 */
	private String resourcePathDesc;

	/**
	 * 访问时间
	 */
	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date created;

	/**
	 * 修改时间
	 */
	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date updated;

}
