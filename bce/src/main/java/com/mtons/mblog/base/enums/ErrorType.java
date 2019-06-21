package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yueny.superclub.api.enums.IResultCodeType;

/**
 * <code>
 *
 * </code>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/18 上午10:02
 */
public enum ErrorType implements IResultCodeType {
    /**
     * 用户不存在
     */
    USER_NOT_EXIST1("1001" , "用户名或密码错误！"),
    /**
     * 用户名与密码不匹配
     */
    USER_OR_PASS_ERROR("1002" , "用户名或密码错误！"),
    /**
     * 入参不合法
     */
    INVALID_ERROR("1101" , "入参不合法:%s"),
    ;

    @EnumValue
    private String code;
    private String message;

    private ErrorType(String errorCode, String describe) {
        code = errorCode;
        message = describe;
    }

    @Override
    @com.yueny.superclub.api.annnotation.EnumValue
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
