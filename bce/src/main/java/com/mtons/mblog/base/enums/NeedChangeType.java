package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 是否需要改变类型
 */
public enum NeedChangeType implements IEnumType {
    /**
     * 不需要
     */
    NO(0),
    /**
     * 需要
     */
    YES(1);


    @EnumValue
    private Integer value;

    NeedChangeType(Integer value){
        this.value = value;
    }

    public static NeedChangeType getBy(Integer value) {
        for(NeedChangeType type : values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public boolean isNeed() {
        return this.value == YES.value;
    }

    @com.yueny.superclub.api.annnotation.EnumValue
    public Integer getValue() {
        return value;
    }
}
