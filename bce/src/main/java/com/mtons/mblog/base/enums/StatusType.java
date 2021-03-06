package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;

/**
 * 状态没救
 */
public enum StatusType implements IEnumType {
    /**
     * 正常，已激活、有效、初始状态
     */
    NORMAL(0, "正常，已激活、有效"),
    /**
     * 关闭、禁用
     */
    CLOSED(1, "关闭、禁用");

    @EnumValue
    private Integer value;

    @Getter
    private String desc;

    StatusType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
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
