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

import lombok.*;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:04
 *
 */
@Getter
@Setter
public class DemoBo extends BaseBo {

	/**
	 * 资产编号
	 */
	private String assetCode;

	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 合同编号， 逗号分隔
	 */
	private String contractNo;

	/**
	 * 数据库中直接数字存储，entry中数字定义
	 *
	 * 担保方式，1信用担保，2保证担保， 3抵押担保， 4质押担保
	 */
	private Integer guaranteeMode;

	private PostBo post;

}
