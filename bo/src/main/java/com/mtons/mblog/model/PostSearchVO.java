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

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.bo.ChannelVO;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.UserBO;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author langhsu
 * 
 */
@Getter
@Setter
public class PostSearchVO extends AbstractMaskBo implements IBo, Serializable {
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
	 * 标签, 多个逗号隔开
	 */
	private String tags;

	private long authorId; // 作者

	private String uid;

	private Date created;

	/**
	 * 收藏数（不同用户）
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
	private BlogFeaturedType featured = BlogFeaturedType.FEATURED_DEFAULT;

	/**
	 * 置顶状态
	 */
	private int weight;

	/**
	 * 预览图编号
	 */
	private String thumbnailCode;


	// 扩展
	/**
	 * 预览图
	 */
	private String thumbnail;

	public String[] getTagsArray() {
		if (StringUtils.isNotBlank(getTags())) {
			return getTags().split(Consts.SEPARATOR);
		}
		return null;
	}

	private ResourceBO resource;

	private UserBO author;

	private ChannelVO channel;

	/**
	 * 编辑器
	 */
	private String editor;
	/**
	 * 内容
	 */
	private String content;

}
