package com.mtons.mblog.service.recipes.triggers;

import com.yueny.superclub.api.enums.core.IEnumType;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/19 上午10:36
 *
 */
public enum TriggerType implements IEnumType {
    /**
     * 博文浏览
     */
    VIEW_ARTICLE_BLOG,
    /**
     * 博文收藏
     */
    favorites_ARTICLE_BLOG,;


    public static TriggerType getBy(String name) {
        for(TriggerType type : values()) {
            if(StringUtils.endsWith(type.name(), name)) {
                return type;
            }
        }
        return null;
    }

}
