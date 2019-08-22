package com.mtons.mblog.condition;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单更新条件
 */
@Getter
@Setter
public class MenuUpdateCondition extends AbstractMaskBo {
    private Long id;

    /**
     * 菜单所在的父类主键。
     */
    private long parentId;

    /**
     * 菜单名
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * url
     */
    private String url;

    /**
     * 所分配的权限主键
     */
    private Long permissionId;
}
