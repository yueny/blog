package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mtons.mblog.entity.api.IEntry;
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
abstract class AbstractIDPlusEntry implements IEntry {
    /** 自然主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)

    @SortableField
    @NumericField
    private Long id;

}
