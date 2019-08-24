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

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 角色-权限关系对象
 *
 * @author langhsu
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RolePermissionBo extends AbstractMaskBo implements IBo, Serializable {

	private Long id;

	private Long roleId;

	private Long permissionId;

	private String uid;

}
