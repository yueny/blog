/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.yueny.kapo.api.annnotation.EntryPk;
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
@TableName("mto_post")

@FilterDefs({
		@FilterDef(name = "POST_STATUS_FILTER", defaultCondition = "status = 0" )})
@Filters({ @Filter(name = "POST_STATUS_FILTER") })
@Indexed(index = "post")
@Analyzer(impl = SmartChineseAnalyzer.class)
@Getter
@Setter
public class Post extends com.yueny.kapo.api.pojo.instance.Entity //extends AbstractPlusEntry
		implements Serializable {
	private static final long serialVersionUID = 7144425803920583495L;

	/** 文章主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SortableField
	@NumericField
	@TableId(type = IdType.AUTO)
	@EntryPk
	private Long id;

	/** 表创建时间 */
	@Column(name = "created", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@TableField(fill = FieldFill.INSERT)
	private Date created;

	/**
	 * 文章编号扩展ID
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
	 * 标签, 多个逗号隔开
	 */
	@Field
	@Column(length = 64)
	private String tags;

	/**
	 * 用户唯一标识，用户编号
	 */
	@Field
	@NumericField
	@Column(name = "author_id")
	private long authorId; // 作者

	@Field
	@Column(name = "uid")
	private String uid;

	/**
	 * 收藏数
	 */
	private int favors;

	/**
	 * 评论数
	 */
	private int comments;

	/**
	 * 阅读次数
	 */
	private int views;

	/**
	 * 文章状态
	 */
	private int status;

	/**
	 * 推荐状态
	 */
	private BlogFeaturedType featured;

	/**
	 * 置顶状态
	 */
	private int weight;

	/**
	 * 预览图编号
	 */
	@Column(name = "thumbnail_code", columnDefinition = "varchar(128)  DEFAULT '' COMMENT '图片资源编号'")
	private String thumbnailCode;

}