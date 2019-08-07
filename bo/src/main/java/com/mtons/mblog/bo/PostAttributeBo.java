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

import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.*;


/**
 * PostAttribute
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostAttributeBo extends AbstractBo implements IBo {
	/**
	 * 数据库主键
	 */
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String editor;

	/**
	 * 内容
	 */
	@Getter
	@Setter
	private String content;

//
//	/**
//	 * 访问时间
//	 */
//	@Getter
//	@Setter
//	@JSONField(format="yyyy-MM-dd HH:MM:ss")
//	private Date created;
//
//	/**
//	 * 修改时间
//	 */
//	@Getter
//	@Setter
//	@JSONField(format="yyyy-MM-dd HH:MM:ss")
//	private Date updated;

}
