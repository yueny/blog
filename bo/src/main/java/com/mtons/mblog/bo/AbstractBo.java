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

import com.alibaba.fastjson.annotation.JSONField;
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
public abstract class AbstractBo extends AbstractIDBo {
	@Getter
	@Setter
	@JSONField(name="created", serialize = true, format = "yyyy-MM-dd HH:mm:ss")
	private Date created;

}
