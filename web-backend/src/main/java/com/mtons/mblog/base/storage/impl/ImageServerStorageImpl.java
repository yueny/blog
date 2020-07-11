package com.mtons.mblog.base.storage.impl;

import com.mtons.mblog.service.comp.configure.ISiteConfigService;
import com.mtons.mblog.service.storage.StorageType;
import com.mtons.mblog.service.storage.impl.AbstractStorage;
import com.mtons.mblog.service.util.file.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 自有图片服务器
 */
@Slf4j
@Component
public class ImageServerStorageImpl extends AbstractStorage {
	@Autowired
	protected ISiteConfigService siteConfigService;

	@Override
	public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
		String dest = siteConfigService.getImageLocationVo().getLocation() + pathAndFileName;
		FileKit.writeByteArrayToFile(bytes, dest);

		String nativeDomain = siteConfigService.getImageLocationVo().getLocationUri();
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
		return StorageType.IMAGE;
	}
}
