package com.mtons.mblog.modules.recipes.triggers.strategy;

import com.mtons.mblog.modules.recipes.triggers.TriggerType;
import org.springframework.stereotype.Component;

/**
 * 博文浏览<br>
 *
 * <pre>
 *  此处处理:
 *      记录浏览的博文和浏览ip数据;
 *      博文浏览数 +1;
 * </pre>
 *
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/19 上午10:28
 */
@Component
public class ViewBlogTrigger extends AbstractTriggersStrategy {
    @Override
    public TriggerType getCondition() {
        return TriggerType.VIEW_ARTICLE_BLOG;
    }
}
