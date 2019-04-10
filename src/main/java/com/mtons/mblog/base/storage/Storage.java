/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author langhsu
 *
 */
public interface Storage {

	/**
	 * 存储图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String store(MultipartFile file, String basePath) throws Exception;

	/**
	 * 存储压缩图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception;

	/**
	 * 存储压缩图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception;

	/**
	 * 存储路径
	 * @param storePath
	 */
	void deleteFile(String storePath);

	/**
	 *
	 * @param pathAndFileName 项目内的文件相对路径， 含文件名
	 * @return 文件全局路径
	 */
	String writeToStore(byte[] bytes, String pathAndFileName) throws Exception;
}
