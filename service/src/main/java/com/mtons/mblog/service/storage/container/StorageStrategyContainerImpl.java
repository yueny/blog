package com.mtons.mblog.service.storage.container;

import com.mtons.mblog.service.storage.Storage;
import com.mtons.mblog.service.storage.StorageType;
import com.yueny.superclub.util.strategy.container.StrategyContainerImpl;
import org.springframework.stereotype.Service;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-28 18:05
 */
@Service
public class StorageStrategyContainerImpl extends StrategyContainerImpl<StorageType, Storage>
        implements IStorageStrategyContainer {
    //.
}
