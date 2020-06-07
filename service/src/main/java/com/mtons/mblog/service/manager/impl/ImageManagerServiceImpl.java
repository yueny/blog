package com.mtons.mblog.service.manager.impl;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.comp.configure.ISiteConfigService;
import com.mtons.mblog.service.exception.BizException;
import com.mtons.mblog.service.manager.IImageManagerService;
import com.mtons.mblog.service.storage.StorageFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    protected ISiteConfigService siteConfigService;
    @Autowired
    protected StorageFactory storageFactory;

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

        // 如果存在域名设置，则获取服务器根目录下的相对路径为 pathName 的值
        String filePath = path;
        // 去掉图片服务器域名, 最终应该由 https://a.b.com/blog/uploads/stoe/blognails/C602B2.jpeg 变为 /stoe/blognails/C602B2.jpeg
        String imageServerUri = siteConfigService.getImageLocationVo().getLocationUri();
        if(StringUtils.isNotBlank(imageServerUri)){
            if(StringUtils.startsWith(path, imageServerUri)){
                filePath = StringUtils.substringAfter(path, imageServerUri);
            }
        }

        // 首先删除服务器图片
        String imageServerLocation = siteConfigService.getImageLocationVo().getLocation();

        storageFactory.get().deleteFile(imageServerLocation + filePath);

        return true;
    }

}
