package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 鉴权类型
 */
public enum AuthoredType implements IEnumType {
    /**
     * 访客
     */
    GUEST(0),
    /**
     * 认证用户
     */
    AUTHORED(1);


    @EnumValue
    private Integer value;

    AuthoredType(Integer value){
        this.value = value;
    }

    public static AuthoredType getBy(Integer value) {
        for(AuthoredType type : values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public boolean isAuthor() {
        return this.value == AUTHORED.value;
    }

    @com.yueny.superclub.api.annnotation.EnumValue
    public Integer getValue() {
        return value;
    }
}
