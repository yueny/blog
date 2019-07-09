package com.mtons.mblog.entity.jpa;

import com.mtons.mblog.base.enums.AttackDenyTimeUnitType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 攻击者IP信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/13 下午2:42
 *
 */
@Entity
@Table(name = "attack_ip",
        indexes = {
        @Index(name = "IK_CLIENT_IP", columnList = "client_ip")
})
@Getter
@Setter
@ToString
public class AttackIpEntry extends BaseEntry {
    /**
     * 客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * attackDenyTimeUnitType
     */
    @Column(name = "deny_time_unit")
    private AttackDenyTimeUnitType denyTimeUnit;
    /**
     * attackDenyTimeUnitType
     */
    @Column(name = "deny_time_val")
    private Long denyTimeVal;

}
