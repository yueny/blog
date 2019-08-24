package com.mtons.mblog.condition;

import com.mtons.mblog.base.enums.FuncType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限更新条件
 */
@Getter
@Setter
public class PermissionUpdateCondition extends AbstractMaskBo {
    private Long id;

    /**
     * 菜单所在的父类主键。
     */
    private long parentId;

    /**
     * 权限配置值
     */
    private String name;

    /**
     * 类型， 0菜单，1功能
     */
    private FuncType funcType;

    /**
     * 权限描述
     */
    private String description;
    /**
     * 权限权重
     */
    private int weight;
}
