package com.mtons.mblog.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.yueny.superclub.api.enums.core.IEnumType;

/**
 * 攻击者拒绝单位时间类型
 */
public enum AttackDenyTimeUnitType implements IEnumType, IEnum {
    /**
     * 小时
     */
    H,
    /**
     * 天
     */
    D,
    /**
     * 年
     */
    Y;

    @Override
    public String getValue() {
        return name();
    }
}
