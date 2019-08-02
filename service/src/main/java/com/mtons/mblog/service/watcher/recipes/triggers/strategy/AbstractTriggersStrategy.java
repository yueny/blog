package com.mtons.mblog.service.watcher.recipes.triggers.strategy;

import com.mtons.mblog.service.watcher.recipes.triggers.ITrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象策略
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/16 下午4:48
 *
 */
public abstract class AbstractTriggersStrategy implements ITrigger {
    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

}
