package com.mtons.mblog.entity.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/** created and id */
@MappedSuperclass
@Getter
@Setter
@ToString
abstract class AbstractJpaEntry extends AbstractIDJpaEntry {

    /**
     * 访问时间
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

}
