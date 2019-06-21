/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.model;

import com.mtons.mblog.bo.ChannelVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 渠道信息树实体
 */
public class ChannelTreeVO extends ChannelVO {
	/* ============== 扩展数据 ====================  */
	/**
	 * 渠道树
	 */
	@Getter
	@Setter
	private List<ChannelTreeVO> children;

	public ChannelTreeVO(){
		//.
	}

}
