/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.entity;

import com.mtons.mblog.base.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.search.annotations.*;

import javax.persistence.Index;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 内容表
 * @author langhsu
 *
 */
@Entity
@Table(name = "mto_post", indexes = {
		@Index(name = "IK_CHANNEL_ID", columnList = "channel_id"),
		@Index(name = "IK_ARTICLE_BLOG_ID", columnList = "article_blog_id")
})
@FilterDefs({
		@FilterDef(name = "POST_STATUS_FILTER", defaultCondition = "status = 0" )})
@Filters({ @Filter(name = "POST_STATUS_FILTER") })
@Indexed(index = "post")
@Analyzer(impl = SmartChineseAnalyzer.class)
@Getter
@Setter
public class Post implements IEntry, Serializable {
	private static final long serialVersionUID = 7144425803920583495L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SortableField
	@NumericField
	private long id;

	/**
	 * 文章扩展ID
	 */
	@Field
	@Column(name = "article_blog_id", length = 64)
	private String articleBlogId;

	/**
	 * 分组/模块ID
	 */
	@Field
	@NumericField
	@Column(name = "channel_id", length = 5)
	private int channelId;

	/**
	 * 标题
	 */
	@Field
	@Column(name = "title", length = 64)
	private String title;

	/**
	 * 摘要
	 */
	@Field
	@Column(length = 140)
	private String summary;

	/**
	 * 预览图
	 */
	@Column(length = 128)
	private String thumbnail;

	/**
	 * 标签, 多个逗号隔开
	 */
	@Field
	@Column(length = 64)
	private String tags;

	@Field
	@NumericField
	@Column(name = "author_id")
	private long authorId; // 作者

	@Temporal(value = TemporalType.TIMESTAMP)
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
}