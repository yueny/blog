package com.mtons.mblog.vo;

import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RoleBO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色对象，扩展了 角色拥有的权限列表信息
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-23 07:51
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RolePermissionVO extends RoleBO {

    private List<PermissionBO> permissions;
}
