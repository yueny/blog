/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.ViewLogBo;
import com.mtons.mblog.condition.ViewerQueryCondition;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 图片资源管理
 */
public interface ViewLogService extends IPlusBizService<ViewLogBo, ViewLogEntry> {
	/**
	 * 条件查询
	 * @param condition 查询条件
	 * @return
	 */
	Page<ViewLogBo> findAllByCondition(Pageable pageable, ViewerQueryCondition condition);
}
