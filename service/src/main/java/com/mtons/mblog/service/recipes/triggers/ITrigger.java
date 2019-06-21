package com.mtons.mblog.service.recipes.triggers;

import com.mtons.mblog.service.recipes.IRecipes;
import com.yueny.superclub.util.strategy.IStrategy;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/19 上午10:30
 */
public interface ITrigger extends IRecipes, IStrategy<TriggerType> {
//    /**
//     * 序列号获取
//     *
//     * @param target 参考数据
//     */
//    String get(String target);
}
