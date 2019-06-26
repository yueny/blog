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

import com.mtons.mblog.base.enums.ResourceType;
import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 图片资源实体
 */
public class ResourceBO extends AbstractBo implements IBo {

	@Getter
	@Setter
	private Long id;

	/**
	 * 图片资源编号
	 */
	@Getter
	@Setter
	private String thumbnailCode;

	/**
	 * md5唯一值
	 */
	@Getter
	@Setter
	private String md5;

	/**
	 * 保存路径
	 */
	@Getter
	@Setter
	private String path;

	@Getter
	@Setter
	private long amount;

	@Getter
	@Setter
	private Date created;

	@Getter
	@Setter
	private Date updated;

	/**
	 * 附件类型
	 */
	@Getter
	@Setter
	private ResourceType resourceType;

}
