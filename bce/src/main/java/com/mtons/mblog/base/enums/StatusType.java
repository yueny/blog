package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 状态没救
 */
public enum StatusType implements IEnumType {
    /**
     * 正常，已激活、有效、初始状态
     */
    NORMAL(0),
    /**
     * 不活跃、关闭、禁用状态
     */
    CLOSED(1);

    @EnumValue
    private Integer value;

    StatusType(Integer value){
        this.value = value;
    }

    public static StatusType getBy(Integer value) {
        for(StatusType type : values()) {
            if(type.getValue().intValue() == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否有效
     * @return
     */
    public boolean isActivate() {
        return this.value == NORMAL.value.intValue();
    }

    @com.yueny.superclub.api.annnotation.EnumValue
    public Integer getValue() {
        return value;
    }
}
