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

import com.mtons.mblog.base.enums.StatusType;
import com.yueny.rapid.lang.common.enums.YesNoType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/18 下午7:58
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RoleBO extends AbstractMaskBo {
	private static final long serialVersionUID = -3482423006644858770L;

	private Long id;

	private String name;

	private String description;

	private StatusType status;

	/**
	 * 是否为超级管理员。 1 是， 0否
	 */
	private YesNoType side;

}
