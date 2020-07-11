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

import com.mtons.mblog.base.consts.options.OptionsKeysConsts;
import com.mtons.mblog.service.comp.configure.ISiteConfigService;
import com.mtons.mblog.service.storage.container.IStorageStrategyContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by langhsu
 * on 2019/1/21
 */
@Component
public class StorageFactory {
//    @Autowired
//    private ApplicationContext applicationContext;
    @Autowired
    private IStorageStrategyContainer container;
    @Autowired
    private ISiteConfigService siteConfigService;

//    private Map<String, Storage> fileRepoMap = new HashMap<>();
//
//    @PostConstruct
//    public void init() {
//        fileRepoMap.put("native", applicationContext.getBean(NativeStorageImpl.class));
//        fileRepoMap.put("upyun", applicationContext.getBean(UpYunStorageImpl.class));
//        fileRepoMap.put("aliyun", applicationContext.getBean(AliyunStorageImpl.class));
//        fileRepoMap.put("qiniu", applicationContext.getBean(QiniuStorageImpl.class));
//        fileRepoMap.put("image", applicationContext.getBean(ImageServerStorageImpl.class));
//    }

    public Storage get() {
        String scheme = siteConfigService.getValue(OptionsKeysConsts.STORAGE_SCHEME);
        return container.get(scheme);
//        if (StringUtils.isBlank(scheme)) {
//            scheme = "native";
//        }
//        return fileRepoMap.get(scheme);
    }
}
