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
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.TagBO;
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
	// 扩展
	private ResourceBO resource;

	private ChannelVO channel;

	/**
	 * 标签, 与属性 tags 对应
	 */
	private List<TagBO> tagsList = new ArrayList<>();

	/**
	 * 编辑器
	 */
	private String editor;
	/**
	 * 内容
	 */
	private String content;

//	/**
//	 * editor和content已在上面赋值， 此处注释
//	 */
//	@JSONField(serialize = false)
//	private PostAttribute attribute;


}
