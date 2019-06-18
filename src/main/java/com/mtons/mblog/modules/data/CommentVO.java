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
import com.mtons.mblog.base.enums.AuthoredType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author langhsu
 */
@Getter
@Setter
public class CommentVO extends AbstractMaskBo implements IBo, Serializable {

	private long id;

	/**
	 * 针对性回复的评论ID(父评论ID)
	 */
	private long pid;

	/**
	 * 所属博文的ID
	 */
	private long postId;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 用户ID（不是用户uid）。 当为0时则表示为匿名用户
	 */
	private Long authorId;
	/**
	 * 用户uid。 匿名用户时为null
	 */
	private String uid;
	/**
	 * 是否为鉴权用户。 1为认证用户(默认)， 0为匿名用户
	 */
	private AuthoredType commitAuthoredType = AuthoredType.AUTHORED;

	private int status;
	/**
	 * 客户端ip
	 */
	private String clientIp;
	/**
	 * 客户端ip
	 */
	private String clientAgent;


	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date created;

	// extend parameter
	private UserCommentModel author;
	// 回复的评论内容
	private CommentVO parent;

	/**
	 * 所评论的博文信息。允许为null
	 */
	private PostVO post;

	/**
	 * 评论相关的用户数据
	 */
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCommentModel extends AbstractMaskBo {
		private String username;

		private String avatar;
		private String name;

		/**
		 * 自定义个性域名
		 */
		@Getter
		@Setter
		private String domainHack;
		/**
		 * 用户唯一标示
		 */
		@Getter
		@Setter
		private String uid;
	}

}
