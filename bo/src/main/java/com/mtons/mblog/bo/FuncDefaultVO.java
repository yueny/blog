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
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 默认值
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午9:27
 *
 */
@Getter
@Setter
public class FuncDefaultVO extends AbstractMaskBo implements IBo, Serializable {

	private long id;

	/**
	 * 预览图
	 */
	private String content;

	/**
	 * 用户uid。 匿名用户时为null
	 */
	private String uid;

	/**
	 * 路径
	 */
	private String clientIp;

	/**
	 * 上传日期
	 */
	@JSONField(format="yyyy-MM-dd HH:MM:ss")
	private Date created;

	/**
	 * 是否生效
	 */
	private int status;

}
