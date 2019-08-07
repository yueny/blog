package com.mtons.mblog.entity.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * updated and created and id
 */
@MappedSuperclass
@Getter
@Setter
@ToString
abstract class AbstractUpdateEntry extends AbstractJpaEntry {

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
