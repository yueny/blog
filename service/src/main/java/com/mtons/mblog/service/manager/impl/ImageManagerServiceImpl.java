package com.mtons.mblog.service.manager.impl;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.comp.configure.IStorageService;
import com.mtons.mblog.service.exception.BizException;
import com.mtons.mblog.service.manager.IImageManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Set;

/**
 * 图片管理服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 16:18
 */
@Service
public class ImageManagerServiceImpl extends BaseService implements IImageManagerService {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    protected IStorageService storageService;
//    @Autowired
//    protected StorageFactory storageFactory;

    @Override
    public boolean delete(String thumbnailCode, String md5) throws BizException {
        return delete(Sets.newHashSet(Flag.builder().thumbnailCode(thumbnailCode).md5(md5).build()));
    }

    @Override
    @Transactional
    public boolean delete(Set<Flag> thumbnailCode) throws BizException {
        for (Flag flag : thumbnailCode) {
            delete(flag);
        }

        return true;
    }

    private boolean delete(Flag flag) throws BizException {
        // 验证图片是否存在
        ResourceBO resourceBO = resourceService.findByThumbnailCode(flag.getThumbnailCode());
        if(resourceBO == null){
            throw new BizException(ErrorType.IMAGE_NOT_FUND_ERROR, flag.getThumbnailCode());
        }

        if(!StringUtils.equals(resourceBO.getMd5(), flag.getMd5())){
            throw new BizException(ErrorType.IMAGE_NOT_FUND_ERROR, flag.getMd5());
        }

        String path = resourceBO.getPath();
        // 去掉图片服务器域名,
        // 最终应该由 https://a.b.com/blog/uploads/stoe/blognails/C602B2.jpeg 变为 /stoe/blognails/C602B2.jpeg
        String imageServerUri = storageService.getLocationUri();

        path = StringUtils.substringAfter(path, imageServerUri);

        // 首先删除服务器图片
        String imageServerLocation = storageService.getLocation();
        File imageFile = new File(imageServerLocation + path);
        if(imageFile.isFile() || imageFile.exists()){
           // 文件夹或文件不存在，均结操作
        }
        return false;
    }
}
