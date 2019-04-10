package com.mtons.mblog.base.storage.impl;

import com.mtons.mblog.base.utils.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 自有图片服务器
 */
@Slf4j
@Component
public class ImageServerStorageImpl extends AbstractStorage {
	private static final String image_server_uri   = "image_server_uri";
	private static final String image_server_location = "image_server_location";

	@Override
	public void deleteFile(String storePath) {
		String path = options.getValue(image_server_location) + storePath;

		File file = new File(path);

		// 文件存在, 且不是目录
		if (file.exists() && !file.isDirectory()) {
			file.delete();
			log.info("fileRepo delete " + storePath);
		}
	}

	@Override
	public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
		String dest = options.getValue(image_server_location) + pathAndFileName;
		FileKit.writeByteArrayToFile(bytes, dest);

		String nativeDomain = options.getValue(image_server_uri);
		if(nativeDomain != null && StringUtils.isNotBlank(nativeDomain)){
			return nativeDomain + pathAndFileName;
		}
		return pathAndFileName;
	}

}
