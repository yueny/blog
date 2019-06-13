package com.mtons.mblog.modules.seq.container;

import com.mtons.mblog.modules.seq.ISeqStrategy;
import com.mtons.mblog.modules.seq.SeqType;
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
public class SeqContainer implements ISeqContainer {
    private IStrategyContainer<SeqType, ISeqStrategy> strategyContainer
            = new StrategyContainerImpl<SeqType, ISeqStrategy>(){};

    @Override
    public ISeqStrategy getStrategy(SeqType type) {
        if(type == null){
            return null;
        }

        ISeqStrategy strategy = strategyContainer.getStrategy(type);

        if(strategy != null){
            return strategy;
        }

        return null;
    }
}
