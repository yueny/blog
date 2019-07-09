package com.mtons.mblog.bo;

import com.mtons.mblog.base.enums.AttackDenyTimeUnitType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 攻击者IP信息
 *
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 17:23
 */
@Getter
@Setter
public class AttackIpBo extends AbstractMaskBo implements IBo, Serializable {
    /** 自然主键 */
    private Long id;

    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 攻击者拒绝单位时间类型， 默认1小时
     */
    private AttackDenyTimeUnitType denyTimeUnit = AttackDenyTimeUnitType.H;
    /**
     * attackDenyTimeUnitType
     */
    private Long denyTimeVal = 1L;

    /**
     * 访问时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;
}
