package com.mtons.mblog.modules.seq;

import com.yueny.superclub.api.enums.core.IEnumType;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午1:43
 *
 */
public enum SeqType implements IEnumType {
    /**
     * 博文ID
     */
    ARTICLE_BLOG_ID;


    public static SeqType getBy(String name) {
        for(SeqType type : values()) {
            if(StringUtils.endsWith(type.name(), name)) {
                return type;
            }
        }
        return null;
    }

}
