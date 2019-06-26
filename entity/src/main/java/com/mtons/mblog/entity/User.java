/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @author langhsu
 *
 */
@Entity
@Table(name = "mto_user")
@Getter
@Setter
public class User implements IEntry, Serializable {
	private static final long serialVersionUID = -3629784071225214858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** 表创建时间 */
	/* 该配置自动创建表 START */
	@TableField(fill = FieldFill.INSERT)
	/* 该配置自动创建表 END */
	private Date created;

	/** 表修改时间 */
	@Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Generated(GenerationTime.ALWAYS)
	@Temporal(value = TemporalType.TIMESTAMP)
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updated;

	/**
	 * 用户唯一标示
	 */
	@Column(name = "uid", unique = true, nullable = false)
	private String uid;

	@Column(name = "username", unique = true, nullable = false, length = 64)
	private String username; // 用户名

	/**
	 * 自定义个性域名
	 */
	@Column(name = "domain_hack", unique = true, nullable = false, length = 64)
	private String domainHack;

	@Column(name = "password", length = 64)
	private String password; // 密码

	/**
	 * 用户头像路径。如果为空，由 thumbnailCode 代替
	 */
	@Deprecated
	private String avatar;
	/**
	 * 上传的头像编号， 取自mto_resource表
	 */
	private String thumbnailCode;

	@Column(name = "name", length = 18)
	private String name;  // 昵称

	private int gender;   // 性别

	@Column(name = "email", unique = true, length = 64)
	private String email;  // 邮箱

	private int posts; // 文章数

	private int comments; // 发布评论数

	@Column(name = "last_login")
	private Date lastLogin;

	private String signature; // 个性签名

	private int status; // 用户状态， 0为可用， 1为不可用

	public User() {
		//.
	}

	public User(long id) {
		this.id = id;
	}

}
