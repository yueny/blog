/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.data.model;

import com.mtons.mblog.base.enums.ChannelNodeType;
import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Builder
@Getter
@Setter
public class ChannelNodeVO extends AbstractBo implements IBo {

	/**
	 * 父渠道ID
	 */
	private int parentChannelId;

	/**
	 * 子渠道节点类型
	 */
	private ChannelNodeType nodeType;

}
