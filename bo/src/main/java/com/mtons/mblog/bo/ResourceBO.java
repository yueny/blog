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
import com.mtons.mblog.base.enums.FileSizeType;
import com.mtons.mblog.base.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 图片资源实体
 */
public class ResourceBO extends AbstractBo {
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

	/**
	 * 文件原始名称
	 */
	@Getter
	@Setter
	private String fileName;
	/**
	 * 文件的大小
	 */
	@Getter
	@Setter
	private Long fileSize;
	/**
	 * 文件大小单位,默认字节
	 */
	@Getter
	@Setter
	private FileSizeType fileSizeType;

	@Getter
	@Setter
	private long amount;

	@Getter
	@Setter
	@JSONField(name="updated", serialize = true, format = "yyyy-MM-dd HH:mm:ss")
	private Date updated;

	/**
	 * 附件类型
	 */
	@Getter
	@Setter
	private ResourceType resourceType;

	public String getResourceTypeDesc(){
		return resourceType.getDesc();
	}

	public String getFileSizeTypeDesc(){
		return fileSizeType.getDesc();
	}

}
