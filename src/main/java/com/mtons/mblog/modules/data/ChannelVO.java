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

import com.mtons.mblog.base.enums.ChannelNodeType;
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
	private Integer id;

	/**
	 * 名称
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * 渠道编号
	 */
	@Getter
	@Setter
	private String channelCode;

	/**
	 * 父渠道编号
	 */
	@Getter
	@Setter
	private String parentChannelCode;


	/**
	 * 对外释义链接url
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

	/**
	 * 状态，0显示； 1隐藏
	 */
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

	/**
	 * 渠道节点类型
	 */
	@Getter
	@Setter
	private ChannelNodeType nodeType;

	// ====================== 数据实体外信息
	/**
	 * 父渠道
	 */
	@Getter
	@Setter
	private ChannelVO parentChannelVo;

	public ChannelVO(){
		//.
	}

	public ChannelVO(String channelCode, String name){
		this.channelCode = channelCode;
		this.name = name;
	}
}
