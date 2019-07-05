/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.manager;

import com.mtons.mblog.bo.ViewLogVO;

/**
 * 浏览记录
 */
public interface IViewLogManagerService {
    /**
     * record
     */
    boolean record(ViewLogVO viewLog);
}
