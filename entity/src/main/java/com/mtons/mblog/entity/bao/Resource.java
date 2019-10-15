package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.base.enums.FileSizeType;
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
@TableName("mto_resource")
public class Resource extends AbstractUpdatePlusEntry implements Serializable {
    private static final long serialVersionUID = -2263990565349962964L;

    /**
     * 图片资源编号
     */
    //驼峰格式会自动转
//    @TableField(value = "thumbnail_code")
//    @Column(name = "thumbnail_code", columnDefinition = "varchar(128) NOT NULL DEFAULT ''")
    @Getter
    @Setter
    private String thumbnailCode;

    /**
     * md5唯一值
     */
    @Getter
    @Setter
    @Column(name = "md5", columnDefinition = "varchar(100) NOT NULL DEFAULT ''")
    private String md5;

    /**
     * 保存路径
     */
    @Getter
    @Setter
    @Column(name = "path", columnDefinition = "varchar(255) NOT NULL DEFAULT ''")
    private String path;

    /**
     * 文件原始名称
     */
    @Getter
    @Setter
    private String fileName;
    /**
     * 文件的大小
     */
    @Getter
    @Setter
    private Long fileSize;
    /**
     * 文件大小单位,默认字节
     */
    @Getter
    @Setter
    private FileSizeType fileSizeType;

    @Getter
    @Setter
    @Column(name = "amount", columnDefinition = "bigint(20) NOT NULL DEFAULT '0'")
    private long amount;

    /**
     * 附件类型
     */
    @Getter
    @Setter
//    @TableField(value = "resource_type")
//    @Column(name = "resource_type", columnDefinition = "varchar(32) NOT NULL DEFAULT 'vague'")
    private ResourceType resourceType;

}
