package com.mtons.mblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/13 下午2:42
 *
 */
/* 该配置自动创建表 START */
@Entity
@Table(name = "demo",
        // indexs 会创建该表 demo 的索引
        indexes = {
        @Index(name = "IK_ORDER_ID", columnList = "orderId")
})
/* 该配置自动创建表 END */
@TableName("demo")
@Getter
@Setter
public class DemoEntry implements IEntry {
    /** 自然主键 */
    /* 该配置自动创建表 START */
    @Id // 会创建自然主键和索引
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 该配置自动创建表 END */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 表创建时间 */
    /* 该配置自动创建表 START */
    @TableField(fill = FieldFill.INSERT)
    /* 该配置自动创建表 END */
    private Date created;

//    /** 表修改时间 */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date modifyTime;

    /**
     * 资产编号
     */
    private String assetCode;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 年龄
     */
    private String age;

    /**
     * 合同编号， 逗号分隔
     */
    private String contractNo;

    /**
     * 数据库中直接数字存储，entry中数字定义
     *
     * 担保方式，1信用担保，2保证担保， 3抵押担保， 4质押担保
     */
    private Integer guaranteeMode;

}
