package com.mtons.mblog.model.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
public class MenuTreeVo {

    /**
     * 父菜单主键
     */
    @Getter
    @Setter
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
