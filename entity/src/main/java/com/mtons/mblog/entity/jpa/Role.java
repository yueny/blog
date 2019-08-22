package com.mtons.mblog.entity.jpa;


import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.entity.bao.Permission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_role")
public class Role extends com.yueny.kapo.api.pojo.instance.Entity implements Serializable {
    private static final long serialVersionUID = -1153854616385727165L;

    public static int STATUS_NORMAL = StatusType.NORMAL.getValue();
    public static int STATUS_CLOSED = StatusType.CLOSED.getValue();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(nullable = false, updatable = false, length = 32)
    @Getter
    @Setter
    private String name;

    @Column(length = 140)
    @Getter
    @Setter
    private String description;

    // StatusType
    @Getter
    @Setter
    private int status;

    @Transient
    @Getter
    @Setter
    private List<Permission> permissions;

}
