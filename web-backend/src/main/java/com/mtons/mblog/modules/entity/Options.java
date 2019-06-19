/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package main.java.com.mtons.mblog.modules.entity;

import com.mtons.mblog.base.api.IEntry;

import javax.persistence.*;

/**
 * 系统配置
 * @author langhsu
 *
 */
@Entity
@Table(name = "mto_options")
public class Options implements IEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * 类型。 0为
	 */
	@Column(length = 5)
	private int type;

	/**
	 * 配置的键
	 */
	@Column(name = "key_", unique = true, length = 32)
	private String key;

	/**
	 * 配置的值
	 */
	@Column(length = 300)
	private String value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
