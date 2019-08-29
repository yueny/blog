package com.mtons.mblog.shiro;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-28 17:45
 */
public interface ShiroRuleFuncNames {
    /**
     * 【仪表盘】仪表盘页面和数据项
     */
    String admin = "admin";
    /**
     * 【仪表盘】不在配置内的所有管理权限(慎重)
     */
    String adminImprove = "admin:improve";
    /**
     * 【仪表盘】缓存
     */
    String adminCache = "admin:cache";

    /**
     * 【栏目管理】栏目页面和数据项
     */
    String channelList = "channel:list";
    /**
     * 【栏目管理】编辑栏目
     */
    String channelUpdate = "channel:update";
    /**
     * 【栏目管理】栏目权重项调整
     */
    String channelWeight = "channel:weight";
    /**
     * 【栏目管理】删除栏目
     */
    String channelDelete = "channel:delete";

    /**
     * 【文章管理】文章页面和数据项
     */
    String postList = "post:list";
    /**
     * 【文章管理】编辑文章
     */
    String postUpdate = "post:update";
    /**
     * 【文章管理】文章权重项调整
     */
    String postWeight = "post:weight";
    /**
     * 【文章管理】删除文章
     */
    String postDelete = "post:delete";

    /**
     * 【评论管理】评论页面和数据项
     */
    String commentList = "comment:list";
    /**
     * 【评论管理】删除评论
     */
    String commentDelete = "comment:delete";

    /**
     * 【系统配置】系统配置更新
     */
    String optionsUpdate = "options:update";

}
