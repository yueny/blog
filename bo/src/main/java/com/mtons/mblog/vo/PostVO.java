/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.vo;

import com.mtons.mblog.bo.*;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author langhsu
 * 
 */
@Getter
@Setter
public class PostVO extends PostBo implements IBo {
	/**
	 * 图片资源
	 */
	private ResourceBO resource;

	/**
	 * 博文特征表
	 */
	private FeatureStatisticsPostBo featureStatisticsPost;

	private ChannelVO channel;

//	/**
//	 * 标签, 与属性 tags 对应
//	 */
//	private List<TagBO> tagsList = new ArrayList<>();


// ############################
// ######### PostAttribute 数据
// ############################
	/**
	 * 编辑器
	 */
	private String editor;
	/**
	 * 内容
	 */
	private String content;

}
