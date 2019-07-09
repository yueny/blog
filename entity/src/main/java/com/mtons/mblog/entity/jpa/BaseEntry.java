package com.mtons.mblog.entity.jpa;

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
abstract class BaseEntry implements IEntry {
    /** 自然主键 */
    @Id // 会创建自然主键和索引
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 访问时间
     */
    @CreatedDate
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

}
