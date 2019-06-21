package com.mtons.mblog.service.recipes.triggers.container;

import com.mtons.mblog.service.recipes.triggers.ITrigger;
import com.mtons.mblog.service.recipes.triggers.TriggerType;

/**
 * 策略容器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/5/17 下午4:45
 */
public interface ITriggerContainer {
    /**
     * 获取策略实现
     */
    ITrigger getStrategy(TriggerType type);

}
