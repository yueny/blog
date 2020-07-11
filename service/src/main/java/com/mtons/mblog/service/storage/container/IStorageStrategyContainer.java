package com.mtons.mblog.service.storage.container;

import com.mtons.mblog.service.storage.Storage;
import com.mtons.mblog.service.storage.StorageType;
import com.yueny.superclub.util.strategy.container.IStrategyContainer;
import org.apache.commons.lang3.StringUtils;

/**
 * 存储的策略容器
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-28 18:04
 */
public interface IStorageStrategyContainer extends IStrategyContainer<StorageType, Storage> {
    default Storage get(String scheme) {
        StorageType storageType = StorageType.NATIVE;
        if (!StringUtils.isBlank(scheme)) {
            storageType = StorageType.get(scheme);
        }

        return get(storageType);
    }

    default Storage get(StorageType scheme) {
        return getStrategy(scheme);
    }
}
