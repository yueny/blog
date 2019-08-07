package com.mtons.mblog.entity.jpa;

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 *  id
 */
/**
 * @MappedSuperclass 这个注解表示在父类上面的，用来标识父类
 * 注意:
 * <p>1.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 * <p>2.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
abstract class AbstractIDJpaEntry implements IEntry {
    /** 自然主键 */
    @Id // 会创建自然主键和索引
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
