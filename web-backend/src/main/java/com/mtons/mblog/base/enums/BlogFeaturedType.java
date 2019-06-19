package com.mtons.mblog.base.enums;

import com.yueny.superclub.api.annnotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 博文推荐状态
 */
public enum BlogFeaturedType implements IEnumType {
    /**
     * 推荐状态-默认(消荐)
     */
    FEATURED_DEFAULT(0, "消荐"),
    /**
     * 推荐状态-推荐(推荐)
     */
    FEATURED_ACTIVE(1, "推荐");

    private int value;
    private String desc;

    BlogFeaturedType(int value, String desc){
        this.value = value;
        this.desc = desc;
    }

    @EnumValue
    public int getValue() {
        return value;
    }

    public static BlogFeaturedType getBy(Integer value) {
        for(BlogFeaturedType type : values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * 博文是否被推荐
     */
    public boolean isFeatured() {
        return this.value == FEATURED_ACTIVE.value;
    }

}
