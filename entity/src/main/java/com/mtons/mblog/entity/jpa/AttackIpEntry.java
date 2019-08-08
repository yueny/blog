package com.mtons.mblog.entity.jpa;

import com.yueny.kapo.api.pojo.IEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

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
public class AttackIpEntry extends com.yueny.kapo.api.pojo.instance.Entity {

    /** 自然主键 */
    @Id // 会创建自然主键和索引
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 访问时间
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * 修改时间
     */
    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(GenerationTime.ALWAYS)
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    /**
     * 客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * attackDenyTimeUnitType
     */
    @Column(name = "deny_time_unit")
    private String denyTimeUnit;
    // dozer 完成 AttackIpBo  和 AttackIpEntry 的AttackDenyTimeUnitType转化
//    private AttackDenyTimeUnitType denyTimeUnit;

    /**
     * attackDenyTimeUnitType
     */
    @Column(name = "deny_time_val")
    private Long denyTimeVal;

}
