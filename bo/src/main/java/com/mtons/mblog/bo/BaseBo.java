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
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/21 上午10:55
 *
 */
@Getter
@Setter
public abstract class BaseBo extends AbstractMaskBo implements IBo, Serializable {

	private Long id;

	private Date created;

//    /** 表修改时间 */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date modifyTime;

}
