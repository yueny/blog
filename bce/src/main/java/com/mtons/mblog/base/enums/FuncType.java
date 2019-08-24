package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;

/**
 * 功能枚举
 */
public enum FuncType implements IEnumType {
    /**
     * 类型 1 功能
     */
    FUNC(StatusType.CLOSED.getValue(), "功能"),
    /**
     * 类型  0 菜单
     */
    MENU(StatusType.NORMAL.getValue(), "菜单");

    @EnumValue
    private Integer value;

    @Getter
    private String desc;

    FuncType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static FuncType getBy(Integer value) {
        for(FuncType type : values()) {
            if(type.getValue().intValue() == value) {
                return type;
            }
        }
        return null;
    }

    @com.yueny.superclub.api.annnotation.EnumValue
    public Integer getValue() {
        return value;
    }

}
