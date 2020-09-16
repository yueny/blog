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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.NumericField;

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
@TableName("mto_user")
@Getter
@Setter
public class User extends AbstractUpdatePlusEntry implements Serializable {
	private static final long serialVersionUID = -3629784071225214858L;

	/**
	 * 用户唯一标示
	 */
	@TableField(value = "uid")
	private String uid;

	@Column(name = "username", unique = true, nullable = false, length = 64)
	@TableField(value = "username")
	private String username; // 用户名

	@Column(name = "name", length = 18)
	@TableField
	private String name;  // 昵称

	/**
	 * 用户头像路径。如果为空，由 thumbnailCode 代替
	 */
	@Deprecated
	@TableField
	private String avatar;
	/**
	 * 上传的头像编号， 取自mto_resource表
	 */
	@TableField
	private String thumbnailCode;

	@Column(name = "email", unique = true, length = 64)
	@TableField
	private String email;  // 邮箱

	@Column(name = "password", length = 64)
	@TableField(value = "password")
	private String password; // 密码

	/**
	 * 0 激活， 1禁用。 用户状态， 0为可用， 1为不可用
	 */
	@TableField
	private Integer status;

	@Column(name = "last_login")
	@TableField
	private Date lastLogin;

	/**
	 * 性别, 1男， 2女， 0未知
	 */
	@TableField
	private Integer gender;

	// role_id 已经删除

	/**
	 * 我的发布文章数
	 */
	@NumericField
	@TableField
	private Integer posts; // 文章数

	/**
	 * 我的发布评论数
	 */
	@NumericField
	@TableField
	private Integer comments; // 发布评论数

	@TableField
	private String signature; // 个性签名

	/**
	 * 自定义个性域名
	 */
	@Column(name = "domain_hack", unique = true, nullable = false, length = 64)
	@TableField(value = "domain_hack")
	private String domainHack;

	public User() {
		//.
	}

	public User(long id) {
		setId(id);
	}

}
