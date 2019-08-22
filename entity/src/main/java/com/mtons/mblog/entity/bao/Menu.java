package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 菜单实体
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 16:59
 */
@Entity
@Table(name = "shiro_menu")
@TableName("shiro_menu")
@Getter
@Setter
public class Menu extends AbstractUpdatePlusEntry {

    /**
     * 菜单名
     */
    @Column(nullable = false, unique = true, length = 255)
    private String name;

    /**
     * 图标
     */
    @Column
    private String icon;

    /**
     * 权重
     */
    @Column
    private int weight;

    /**
     * 菜单链接地址
     */
    private String url;

    /**
     * 权限主键
     */
    @Column(name = "permission_id", updatable = false)
    private Long permissionId;

    /**
     * 父菜单主键
     */
    @Column(name = "parent_id")
    private Long parentId;
}
