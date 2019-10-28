package com.mtons.mblog.service.storage;

import org.apache.commons.lang3.StringUtils;

/**
 * 图片存储服务器的方式
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-28 17:51
 */
public enum StorageType {
    /**
     * 服务器本地存储
     */
    NATIVE,

    /**
     * 自有图片服务器存储
     */
    IMAGE,

    /**
     * 又拍云存储
     */
    UPYUN,

    /**
     * 阿里云存储
     */
    ALIYUN,

    /**
     * 七牛云存储
     */
    QINIU;

    public static StorageType get(StorageType storageType){
        return get(storageType.name());
    }

    public static StorageType get(String storageTypeVal){
        for (StorageType type: values()) {
            if(StringUtils.equalsIgnoreCase(storageTypeVal, type.name())) {
                return type;
            }
        }

        // 默认
        return StorageType.NATIVE;
    }
}