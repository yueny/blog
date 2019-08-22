package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单权限
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_permission")
@TableName("shiro_permission")
@Getter
@Setter
public class Permission extends AbstractIDPlusEntry implements Serializable {
    private static final long serialVersionUID = -5979636077639378677L;

    @Column(name = "parent_id", updatable = false)
    private long parentId;
    
    @Column(nullable = false, unique = true, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    private int weight;

    /**
     * @Version 乐观锁注解、标记
     */
    // set version = version+1 where version = version
    @Version
    // insert, set version = 0
    @TableField(fill = FieldFill.INSERT)
//    @TableField(value="version", fill = FieldFill.INSERT, update="%s+1")
    private Integer version;

}
