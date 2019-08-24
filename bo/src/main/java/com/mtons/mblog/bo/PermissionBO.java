/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.bo;

import com.mtons.mblog.base.enums.FuncType;
import com.mtons.mblog.base.enums.StatusType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单权限
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/18 下午7:58
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PermissionBO extends AbstractMaskBo implements IBo, Serializable {
	private long id;

	private long parentId;

	private String name;

	private String description;

	/**
	 * 类型， 0菜单，1功能
	 */
	private FuncType funcType;

	private int weight;

	private Integer version;

}
