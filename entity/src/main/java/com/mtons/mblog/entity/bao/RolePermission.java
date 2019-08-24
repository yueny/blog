package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 角色-权限关系对象
 *
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_role_permission")
@TableName("shiro_role_permission")
@Getter
@Setter
@ToString
public class RolePermission extends AbstractIDPlusEntry {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "uid")
    @Getter
    @Setter
    private String uid;
}
