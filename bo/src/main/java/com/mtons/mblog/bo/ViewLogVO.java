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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewLogVO extends AbstractBo implements IBo {
	/**
	 * 数据库主键
	 */
	@Getter
	@Setter
	private Long id;

	/**
	 * 客户端ip
	 */
	@Getter
	@Setter
	private String clientIp;
	/**
	 * 客户端 agent
	 */
	@Getter
	@Setter
	private String clientAgent;

	/**
	 * 访问资源路径
	 */
	@Getter
	@Setter
	private String resourcePath;
	/**
	 * 访问资源路径描述
	 */
	@Getter
	@Setter
	private String resourcePathDesc;

	/**
	 * 访问时间
	 */
	@Getter
	@Setter
	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date created;

	/**
	 * 修改时间
	 */
	@Getter
	@Setter
	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date updated;

}
