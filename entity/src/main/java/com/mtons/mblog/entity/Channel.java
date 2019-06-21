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

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 模块/内容分组
 * @author langhsu
 *
 */
@Entity
@Table(name = "mto_channel")
public class Channel implements IEntry, Serializable {
	private static final long serialVersionUID = 2436696690653745208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 渠道编号
	 */
	@Column(name = "channel_code", unique = true, length = 64)
	@Getter
	@Setter
	private String channelCode;

	/**
	 * 父渠道编号
	 */
	@Column(name = "parent_channel_code", length = 64)
	@Getter
	@Setter
	private String parentChannelCode;

	/**
	 * 对外释义标识url
	 */
	@Column(name = "flag", unique = true, length = 256)
	@Getter
	@Setter
	private String flag;

	/**
	 * 唯一关键字
	 */
	@Column(name = "key_", unique = true, length = 32)
	private String key;

	/**
	 * 图片资源编号， 取自 Resource 表
	 */
	@Column(name = "thumbnail_code")
	@Getter
	@Setter
	private String thumbnailCode;

	/**
	 * 渠道节点类型
	 */
	@Column(name = "node_type")
	@Getter
	@Setter
	private String nodeType;

	/**
	 * 状态，0显示； 1隐藏
	 */
	@Column(length = 5)
	private int status;

	/**
	 * 排序值
	 */
	private int weight;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
