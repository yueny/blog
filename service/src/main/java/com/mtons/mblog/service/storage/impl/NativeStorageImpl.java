/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.storage.impl;

import com.mtons.mblog.service.comp.configure.ISiteConfigService;
import com.mtons.mblog.service.storage.StorageType;
import com.mtons.mblog.service.util.file.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 服务器本地存储
 *
 * <pre>
 *      image_server_uri=http://localhost:8090/
 * 		image_server_location=/Users/xiaobai/temp/workspace/yueny09/github/mblog/blog/uploads
 * </pre>
 *
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class NativeStorageImpl extends AbstractStorage {
	@Autowired
	protected ISiteConfigService siteConfigService;

	@Override
	public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
		String dest = siteConfigService.getNativeLocationVo().getLocation() + pathAndFileName;
		FileKit.writeByteArrayToFile(bytes, dest);

		String nativeDomain = siteConfigService.getNativeLocationVo().getLocationUri();
		if(nativeDomain != null && StringUtils.isNotBlank(nativeDomain)){
			return nativeDomain + pathAndFileName;
		}
		return pathAndFileName;
	}

	@Override
	public void deleteFile(String storePath) {
		File imageFile = new File(storePath);

		// 文件存在, 且不是目录
		if (imageFile.exists() && !imageFile.isDirectory()) {
			imageFile.delete();
			log.info("fileRepo delete " + storePath);
		}
	}

	@Override
	public StorageType getCondition() {
		return StorageType.NATIVE;
	}
}
