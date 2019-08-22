package com.mtons.mblog.model.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
@Getter
@Setter
public class MenuTreeVo {

    /**
     * 父菜单主键
     */
    private Long parentId;

    /**
     * 图标
     */
    private String icon;
    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单链接地址
     */
    private String url;

    /**
     * 权限主键
     */
    private Long permissionId;
    /**
     * 菜单权限值
     */
    private String permission;

    /**
     * 子菜单
     */
    @Getter
    @Setter
    private List<MenuTreeVo> children;

    public void addChildren(MenuTreeVo item){
        if(this.children == null){
            this.children = new LinkedList<>();
        }
        this.children.add(item);
    }
}
