package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * created and id
 */
@MappedSuperclass
@Getter
@Setter
@ToString
abstract class AbstractPlusEntry extends AbstractIDPlusEntry {
    /** 表创建时间 */
    @Column(name = "created", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @TableField(fill = FieldFill.INSERT)
    private Date created;

}
