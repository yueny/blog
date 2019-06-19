/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package main.java.com.mtons.mblog.base.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author langhsu
 *
 */
public interface Storage {

	/**
	 * 存储图片
	 * @param file
	 * @param nailPath
	 * @return key: thumbnailCode 图片资源编号; value: path 图片资源路径
	 * @throws IOException
	 */
	Map.Entry<String, String> store(MultipartFile file, NailPathData nailPath) throws Exception;

	/**
	 * 存储压缩图片
	 * @param file
	 * @param nailPath
	 * @return key: thumbnailCode 图片资源编号; value: path 图片资源路径
	 * @throws IOException
	 */
	Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int maxWidth) throws Exception;

	/**
	 * 存储压缩图片
	 * @param file
	 * @param nailPath
	 * @return key: thumbnailCode 图片资源编号; value: path 图片资源路径
	 * @throws IOException
	 */
	Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int width, int height) throws Exception;

	/**
	 * 存储路径
	 * @param storePath
	 */
	void deleteFile(String storePath);

	/**
	 *
	 * @param pathAndFileName 项目内的文件相对路径， 含文件名
	 * @return  图片资源全局路径
	 */
	String writeToStore(byte[] bytes, String pathAndFileName) throws Exception;
}
