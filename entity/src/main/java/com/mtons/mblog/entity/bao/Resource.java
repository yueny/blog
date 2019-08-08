package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.base.enums.ResourceType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 图片资源表
 *
 * @author saxing 2019/4/3 21:24
 */
@Data
@Entity
@Table(name = "mto_resource",
        uniqueConstraints = {@UniqueConstraint(name = "UK_MD5", columnNames = {"md5"})}
)
@TableName("mto_resource")
public class Resource extends AbstractUpdatePlusEntry implements Serializable {
    private static final long serialVersionUID = -2263990565349962964L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @TableId(type = IdType.AUTO)
//    private Long id;
//
//    @Column(name = "created", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP")
//    @Temporal(TemporalType.TIMESTAMP)
//    @TableField(fill = FieldFill.INSERT)
//    private Date created;
//
//    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    @Generated(GenerationTime.ALWAYS)
//    @Temporal(value = TemporalType.TIMESTAMP)
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updated;



    /**
     * 图片资源编号
     */
    @Column(name = "thumbnail_code", columnDefinition = "varchar(128) NOT NULL DEFAULT ''")
    @Getter
    @Setter
    private String thumbnailCode;

    /**
     * md5唯一值
     */
    @Column(name = "md5", columnDefinition = "varchar(100) NOT NULL DEFAULT ''")
    private String md5;

    /**
     * 保存路径
     */
    @Column(name = "path", columnDefinition = "varchar(255) NOT NULL DEFAULT ''")
    private String path;

    @Column(name = "amount", columnDefinition = "bigint(20) NOT NULL DEFAULT '0'")
    private long amount;

    /**
     * 附件类型
     */
    @Getter
    @Setter
    @Column(name = "resource_type", columnDefinition = "varchar(32) NOT NULL DEFAULT 'vague'")
    private ResourceType resourceType;

}
