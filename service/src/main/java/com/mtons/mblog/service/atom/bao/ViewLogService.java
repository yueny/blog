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

import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.api.bao.IPlusBizService;

import java.util.List;

/**
 * 图片资源管理
 */
public interface ViewLogService extends IPlusBizService<ViewLogVO, ViewLogEntry> {
	List<ViewLogVO> findListByIp(String ip);
}
