package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 用户角色映射表
 *
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_user_role")
@TableName("shiro_user_role")
@Getter
@Setter
@ToString
public class UserRole extends AbstractIDPlusEntry {
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
