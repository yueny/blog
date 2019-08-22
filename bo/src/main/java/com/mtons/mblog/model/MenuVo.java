package com.mtons.mblog.model;

import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.bo.PermissionBO;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单业务实体
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 17:34
 */
@Getter
@Setter
public class MenuVo extends MenuBo {

    /**
     * 权限对象
     */
    private PermissionBO permission;
}
