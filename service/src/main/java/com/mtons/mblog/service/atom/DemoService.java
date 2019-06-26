/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom;

import com.mtons.mblog.bo.DemoVO;

import java.util.List;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:05
 *
 */
public interface DemoService {
	/**
	 * 根据订单号查询， 执行于 xml文件中
	 */
	DemoVO selectByOrderId(String orderId);

	/**
	 * 担保方式查询
	 *
	 * @param  guaranteeMode 担保方式，1信用担保，2保证担保， 3抵押担保， 4质押担保
	 *
	 */
	List<DemoVO> selectListByGuaranteeMode(Integer guaranteeMode);
}
