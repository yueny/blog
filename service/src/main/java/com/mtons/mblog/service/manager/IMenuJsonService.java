package com.mtons.mblog.service.manager;

import com.mtons.mblog.vo.menu.MenuTreeVo;

import java.util.List;

/**
 * 菜单加载服务
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 21:01
 */
public interface IMenuJsonService {
    /**
     * 获取菜单
     *
     * @return
     */
    List<MenuTreeVo> getMenus();

    /**
     * 重新加载
     * @return
     */
    boolean reload();
}
