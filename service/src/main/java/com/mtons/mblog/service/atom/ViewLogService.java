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

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.entity.ViewLogEntry;

import java.util.List;

/**
 * 图片资源管理
 */
public interface ViewLogService extends IService<ViewLogEntry> {
	List<ViewLogVO> findListByIp(String ip);
}
