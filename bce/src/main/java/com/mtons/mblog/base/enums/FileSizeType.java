package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;

/**
 * 文件大小单位
 */
public enum FileSizeType implements IEnumType {
    /**
     * 字节
     */
    BYTE("byte", "字节");

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

    FileSizeType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static FileSizeType getBy(String value) {
        for(FileSizeType type : values()) {
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
