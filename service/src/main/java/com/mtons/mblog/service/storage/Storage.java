/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.storage;

import com.yueny.superclub.util.strategy.IStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 *
 */
public interface Storage extends IStrategy<StorageType> {

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
	 * @return key: thumbnailCode 图片资源编号; value: path 图片资源路径， 如/storage/thumbnails/_signature/4BS6MB2OMJBO6V3S05194K3C8.png
	 * @throws IOException
	 */
	Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int width, int height) throws Exception;

	/**
	 * 删除文件
	 * @param storePath 文件服务器识别的文件有效目录
	 */
	void deleteFile(String storePath);

	/**
	 * 只做图片存储操作
	 *
	 * @param pathAndFileName 项目内的文件相对路径， 含文件名
	 * @return  图片资源全局路径
	 */
	String writeToStore(byte[] bytes, String pathAndFileName) throws Exception;

	/**
	 * 获取服务器上的图片列表
	 * @return  图片地址集合
	 */
	Set<String> filesList();
}
