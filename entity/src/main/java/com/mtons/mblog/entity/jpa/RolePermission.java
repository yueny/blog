package com.mtons.mblog.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色-权限值
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_role_permission")
public class RolePermission extends com.yueny.kapo.api.pojo.instance.Entity implements Serializable {
    private static final long serialVersionUID = -5979636077649378677L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "uid")
    @Getter
    @Setter
    private String uid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
