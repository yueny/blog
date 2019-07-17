package com.mtons.mblog.entity.jpa;

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色映射表
 *
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_user_role")
public class UserRole implements IEntry, Serializable {
	private static final long serialVersionUID = -2908144287976184011L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "user_id")
	private Long userId;

	/**
	 * 用户唯一标示
	 */
	@Column(name = "uid", unique = true, nullable = false)
	@Getter
	@Setter
	private String uid;

	@Column(name = "role_id")
    private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
