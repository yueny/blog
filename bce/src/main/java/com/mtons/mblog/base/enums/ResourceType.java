package com.mtons.mblog.base.enums;

import com.yueny.superclub.api.annnotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 图片资源类型
 */
public enum ResourceType implements IEnumType {
    /**
     * 头像
     */
    AVATARS("avatars"),
    /**
     * 博文中的附件
     */
    BLOG_ATTR("blogAttr"),
    /**
     * 栏目缩略图
     */
    THUMB_CHANNEL("thumbChannel"),
    /**
     * 博文缩略图
     */
    THUMB_BLOG("thumbBlog"),
    /**
     * 其他部分单独分类的缩略图
     */
    THUMB("thumb"),
    /**
     * 不属于上述分类的储存资源
     */
    VAGUE("vague");

    private String value;

    ResourceType(String value){
        this.value = value;
    }

    @EnumValue
    public String getValue() {
        return value;
    }

    public static ResourceType getBy(String value) {
        for(ResourceType type : values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

}
