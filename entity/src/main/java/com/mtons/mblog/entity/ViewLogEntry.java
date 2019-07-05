package com.mtons.mblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/13 下午2:42
 *
 */
@Entity
@Table(name = "view_log",
        indexes = {
        @Index(name = "IK_CLIENT_IP", columnList = "client_ip")
})
@TableName("view_log")
@Getter
@Setter
@ToString
public class ViewLogEntry implements IEntry {
    /** 自然主键 */
    @Id // 会创建自然主键和索引
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * 客户端 agent
     */
    @Column(name = "client_agent")
    private String clientAgent;

    /**
     * 访问资源地址
     */
    @Column(name = "resource_path")
    private String resourcePath;
    /**
     * 访问资源路径描述
     */
    @Column(name = "resource_path_desc")
    private String resourcePathDesc;

    /**
     * 访问时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 修改时间
     */
    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(GenerationTime.ALWAYS)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updated;

}
