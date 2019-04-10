/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.config;

import com.taobao.diamond.extend.DynamicProperties;
import lombok.ToString;

/**
 * 读取配置中心配置信息
 */
@ToString
public abstract class SiteConfigOption {
    protected static final String SITE_VERSION_KEY = "site.version";
    /* 注册开关 */
    protected static final String SITE_CONTROLS_REGISTER_KEY = "site.controls.register";
    /*  登陆开关是否显示 */
    protected static final String SITE_CONTROLS_LOGIN_SHOW_KEY = "site.controls.login_show";
    /* 发布文章开关 */
    protected static final String SITE_CONTROLS_POST_KEY = "site.controls.post";
    /* 评论开关, true 为允许评论 */
    protected static final String SITE_CONTROLS_COMMENT_KEY = "site.controls.comment";
    /* 是否允许匿名评论开关, true 为允许匿名评论 */
    protected static final String SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY = "site.controls.comment.allow.anonymous";

    //    site.controls.register_email_validate=false

    /**
     * 系统版本号s
     */
    private String version;

    public String getVersion() {
        return DynamicProperties.staticProperties.getProperty(SITE_VERSION_KEY);
    }

    @ToString
    public static class Control {
        private boolean register;
        private boolean post;
        private boolean comment;

        private boolean login_show; // 是否显示注册按钮

        /* 是否允许匿名评论开关, true 为允许匿名评论 */
        private boolean commentAllowAnonymous;

        public boolean isRegister() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_REGISTER_KEY);
            return Boolean.valueOf(val);
        }

        public boolean isLogin_show() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_LOGIN_SHOW_KEY);
            return Boolean.valueOf(val);
        }

        public boolean isPost() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_POST_KEY);
            return Boolean.valueOf(val);
        }

        public boolean isComment() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_KEY);
            return Boolean.valueOf(val);
        }

        public boolean isCommentAllowAnonymous() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY);
            return Boolean.valueOf(val);
        }

    }

}
