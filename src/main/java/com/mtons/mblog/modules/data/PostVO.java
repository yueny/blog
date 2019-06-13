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

import com.alibaba.fastjson.annotation.JSONField;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.entity.PostAttribute;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.Date;

/**
 * @author langhsu
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostVO implements IBo, Serializable {
	private long id;

	/**
	 * 文章扩展ID
	 */
	private String articleBlogId;

	/**
	 * 分组/模块ID
	 */
	private int channelId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 预览图
	 */
	private String thumbnail;

	/**
	 * 标签, 多个逗号隔开
	 */
	private String tags;

	private long authorId; // 作者

	private Date created;

	/**
	 * 收藏数
	 */
	private int favors;

	/**
	 * 评论数
	 */
	private int comments;

	/**
	 * 阅读数
	 */
	private int views;

	/**
	 * 文章状态
	 */
	private int status;

	/**
	 * 推荐状态
	 */
	private int featured;

	/**
	 * 置顶状态
	 */
	private int weight;



	private UserVO author;

	private ChannelVO channel;

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
	
	public String[] getTagsArray() {
		if (StringUtils.isNotBlank(getTags())) {
			return getTags().split(Consts.SEPARATOR);
		}
		return null;
	}

}
