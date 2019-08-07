package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * updated and created and id
 */
@MappedSuperclass
@Getter
@Setter
@ToString
abstract class AbstractUpdatePlusEntry extends AbstractPlusEntry {
    /** 表修改时间 */
    @Column(name = "updated", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(GenerationTime.ALWAYS)
    @Temporal(value = TemporalType.TIMESTAMP)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

}
