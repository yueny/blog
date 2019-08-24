package com.mtons.mblog.entity.bao;


import com.baomidou.mybatisplus.annotation.TableName;
import com.mtons.mblog.base.enums.StatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 角色
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_role")
@TableName("shiro_role")
@Getter
@Setter
@ToString
public class Role extends AbstractIDPlusEntry {
    public static int STATUS_NORMAL = StatusType.NORMAL.getValue();
    public static int STATUS_CLOSED = StatusType.CLOSED.getValue();

    @Column(nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    /**
     * 状态
     */
    private StatusType status;




//
//    /**
//     * 非数据库字段
//     */
//    @Transient
//    @Getter
//    @Setter
//    private List<Permission> permissions;

}
