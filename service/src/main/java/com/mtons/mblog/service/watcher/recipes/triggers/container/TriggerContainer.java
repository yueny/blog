package com.mtons.mblog.service.watcher.recipes.triggers.container;

import com.mtons.mblog.service.watcher.recipes.triggers.ITrigger;
import com.mtons.mblog.service.watcher.recipes.triggers.TriggerType;
import com.yueny.superclub.util.strategy.container.IStrategyContainer;
import com.yueny.superclub.util.strategy.container.StrategyContainerImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午1:48
 *
 */
@Component
public class TriggerContainer implements ITriggerContainer {
    private IStrategyContainer<TriggerType, ITrigger> strategyContainer
            = new StrategyContainerImpl<TriggerType, ITrigger>(){};

    @Override
    public ITrigger getStrategy(TriggerType type) {
        if(type == null){
            return null;
        }

        ITrigger strategy = strategyContainer.getStrategy(type);

        if(strategy != null){
            return strategy;
        }

        return null;
    }
}
