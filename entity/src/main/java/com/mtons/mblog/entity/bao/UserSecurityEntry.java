package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.base.enums.NeedChangeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 用户安全配置表
 */
/* 该配置自动创建表 START */
@Entity
@Table(name = "mto_user_security",
        // indexs 会创建该表 的索引
        indexes = {
        @Index(name = "IK_UID", columnList = "uid")
})
/* 该配置自动创建表 END */
@TableName("mto_user_security")
@Getter
@Setter
@ToString
public class UserSecurityEntry extends AbstractUpdatePlusEntry {
//    /** 自然主键 */
//    /* 该配置自动创建表 START */
//    @Id // 会创建自然主键和索引
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    /* 该配置自动创建表 END */
//    @TableId(type = IdType.AUTO)
//    private Long id;
//
//    /** 表创建时间 */
//    @Temporal(TemporalType.TIMESTAMP)
//    @TableField(fill = FieldFill.INSERT)
//    private Date created;
//
//    /** 表修改时间 */
//    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    @Generated(GenerationTime.ALWAYS)
//    @Temporal(value = TemporalType.TIMESTAMP)
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updated;

    /**
     * 用户唯一标示
     */
    @Column(name = "uid", unique = true, nullable = false)
    private String uid;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 是否需要修改密码， 0为不需要，1为需要修改密码
     */
    private NeedChangeType needChangePw;

}
