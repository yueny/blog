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
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.service.storage.NailPathData;
import com.mtons.mblog.service.storage.Storage;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.service.atom.bao.ResourceManagerService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.util.ImageUtils;
import com.mtons.mblog.service.util.MD5;
import com.mtons.mblog.service.util.file.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author langhsu
 * @since 3.0
 */
@Slf4j
public abstract class AbstractStorage implements Storage {
    @Autowired
    protected ResourceService resourceService;
    @Autowired
    protected ResourceManagerService resourceManagerService;
    @Autowired
    protected ISiteConfigService siteConfigService;

    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new MtonsException("文件不能为空");
        }

        if (!FileKit.checkFileType(file.getOriginalFilename())) {
            throw new MtonsException("文件格式不支持");
        }
    }

    @Override
    public Map.Entry<String, String> store(MultipartFile file, NailPathData nailPath) throws Exception {
        validateFile(file);
        return writeToStore(file.getBytes(), nailPath);
    }

    @Override
    public Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int maxWidth) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.scaleByWidth(file, maxWidth);
        return writeToStore(bytes, nailPath);
    }

    @Override
    public Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int width, int height) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.screenshot(file, width, height);
        return writeToStore(bytes, nailPath);
    }

    @Override
    public Set<String> filesList() {
        Set<String> list = new HashSet<>();

        // TODO 此处暂时未实现，默认空。
        return list;
    }

    public Map.Entry<String, String> writeToStore(byte[] bytes, NailPathData nailPath) throws Exception {
        String md5 = MD5.md5File(bytes);
        ResourceBO resourceBO = resourceService.findByMd5(md5);
        if (resourceBO != null){
            // 图片已经存在了。 不在上传，直接使用。此处使用数应该+1
            return new AbstractMap.SimpleEntry<>(resourceBO.getThumbnailCode(), resourceBO.getPath());
        }

        String path = siteConfigService.getWholePathName(nailPath, md5);
        String fullPath = writeToStore(bytes, path);

        // 图片入库存储
        resourceBO = new ResourceBO();
        resourceBO.setFileName(nailPath.getOriginalFilename());
        resourceBO.setFileSize(nailPath.getSize());
        resourceBO.setFileSizeType(nailPath.getFileSizeType());
        resourceBO.setMd5(md5);
        resourceBO.setPath(fullPath);
        resourceBO.setResourceType(nailPath.getNailType().getResourceType());
        String thumbnailCode = resourceManagerService.save(resourceBO);

        return new AbstractMap.SimpleEntry<>(thumbnailCode, fullPath);
    }

}
