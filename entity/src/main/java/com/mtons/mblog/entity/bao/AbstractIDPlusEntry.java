package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yueny.kapo.api.annnotation.EntryPk;
import com.yueny.kapo.api.pojo.instance.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.SortableField;

import javax.persistence.*;

/**
 * id
 */
@MappedSuperclass
@Getter
@Setter
@ToString
abstract class AbstractIDPlusEntry extends Entity {
    /** 自然主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)

    @SortableField
    @NumericField
    @EntryPk
    private Long id;

}
