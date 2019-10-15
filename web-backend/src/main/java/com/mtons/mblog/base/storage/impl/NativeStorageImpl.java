/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.storage.impl;

import com.mtons.mblog.service.util.file.FileKit;
import com.mtons.mblog.service.comp.configure.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class NativeStorageImpl extends AbstractStorage {
	@Autowired
	protected IStorageService storageService;

	@Override
	public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
		String dest = storageService.getLocation() + pathAndFileName;
		FileKit.writeByteArrayToFile(bytes, dest);

		String nativeDomain = storageService.getLocationUri();
		if(nativeDomain != null && StringUtils.isNotBlank(nativeDomain)){
			return nativeDomain + pathAndFileName;
		}
		return pathAndFileName;
	}

	@Override
	public void deleteFile(String storePath) {
		File file = new File(storageService.getLocation() + storePath);

		// 文件存在, 且不是目录
		if (file.exists() && !file.isDirectory()) {
			file.delete();
			log.info("fileRepo delete " + storePath);
		}
	}

}
