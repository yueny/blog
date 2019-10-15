/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import org.springframework.core.io.Resource;

/**
 * @author langhsu
 *
 */
public interface EntityService {
	void initSettings(Resource resource);
}
