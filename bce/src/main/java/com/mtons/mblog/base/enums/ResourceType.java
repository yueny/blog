package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 图片资源类型
 */
public enum ResourceType implements IEnumType {
    /**
     * 头像
     */
    AVATARS("avatars", "头像"),
    /**
     * 博文中的附件
     */
    BLOG_ATTR("blogAttr", "博文附件图片"),
    /**
     * 栏目缩略图
     */
    THUMB_CHANNEL("thumbChannel", "栏目缩略图"),
    /**
     * 博文缩略图
     */
    THUMB_BLOG("thumbBlog", "博文缩略图"),
    /**
     * 其他部分单独分类的缩略图
     */
    THUMB("thumb", "其他缩略图"),
    /**
     * 不属于上述分类的储存资源
     */
    VAGUE("vague", "其他储存资源");

    public void setValue(String value) {
        this.value = value;
    }

    @EnumValue
    private String value;

    /**
     * 描述
     */
    @Getter
    private String desc;

    ResourceType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static ResourceType getBy(String value) {
        for(ResourceType type : values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    @com.yueny.superclub.api.annnotation.EnumValue
    public String getValue() {
        return value;
    }
}
