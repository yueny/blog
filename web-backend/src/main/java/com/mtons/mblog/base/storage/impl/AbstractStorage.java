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

import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.storage.NailPathData;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.*;
import com.mtons.mblog.config.SiteOptions;
import com.mtons.mblog.modules.data.ResourceVO;
import com.mtons.mblog.modules.service.ResourceManagerService;
import com.mtons.mblog.modules.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author langhsu
 * @since 3.0
 */
@Slf4j
public abstract class AbstractStorage implements Storage {
    @Autowired
    protected SiteOptions options;
    @Autowired
    protected ResourceService resourceService;
    @Autowired
    protected ResourceManagerService resourceManagerService;

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
        return writeToStore(file.getBytes(), nailPath, file.getOriginalFilename());
    }

    @Override
    public Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int maxWidth) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.scaleByWidth(file, maxWidth);
        return writeToStore(bytes, nailPath, file.getOriginalFilename());
    }

    @Override
    public Map.Entry<String, String> storeScale(MultipartFile file, NailPathData nailPath, int width, int height) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.screenshot(file, width, height);
        return writeToStore(bytes, nailPath, file.getOriginalFilename());
    }

    public Map.Entry<String, String> writeToStore(byte[] bytes, NailPathData nailPath, String originalFilename) throws Exception {
        String md5 = MD5.md5File(bytes);
        ResourceVO resourceVo = resourceService.findByMd5(md5);
        if (resourceVo != null){
            return new AbstractMap.SimpleEntry<>(resourceVo.getThumbnailCode(), resourceVo.getPath());
        }

        String path = FilePathUtils.wholePathName(nailPath.get(), originalFilename, md5);
        String fullPath = writeToStore(bytes, path);

        // 图片入库
        resourceVo = new ResourceVO();
        resourceVo.setMd5(md5);
        resourceVo.setPath(fullPath);
        String thumbnailCode = resourceManagerService.save(resourceVo);

        return new AbstractMap.SimpleEntry<>(thumbnailCode, fullPath);
    }

}
