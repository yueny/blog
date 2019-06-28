/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.config.options;

import com.taobao.diamond.extend.DynamicProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

/**
 * 读取配置中心配置信息
 */
@ToString
public abstract class SiteConfigOption {
    protected static final String SITE_VERSION_KEY = "site.version";

    /**
     *  controls 的一系列配置 site.controls.*
     */
    /* 注册开关 */
    private static final String SITE_CONTROLS_REGISTER_KEY = "site.controls.register";
    /*  登陆开关是否显示 */
    private static final String SITE_CONTROLS_LOGIN_SHOW_KEY = "site.controls.login_show";
    /* 发布文章开关 */
    private static final String SITE_CONTROLS_POST_KEY = "site.controls.post";
    /* 评论开关, true 为允许评论 */
    private static final String SITE_CONTROLS_COMMENT_KEY = "site.controls.comment";
    /* 是否允许匿名评论开关, true 为允许匿名评论 */
    private static final String SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY = "site.controls.comment.allow.anonymous";

    /**
     *  default 的一系列配置 site.settings.*
     */
    /* 用户的默认头像 */
    private static final String SITE_SETTINGS_USERAVATAR_KEY = "site.settings.userAvatar";

    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////

    /**
     * controls
     */
    @ToString
    public static class Control {
        private boolean register;
        private boolean post;
        private boolean comment;

        // 是否显示注册按钮
        private boolean login_show;

        /* 是否允许匿名评论开关, true 为允许匿名评论 */
        private boolean commentAllowAnonymous;

        public boolean isRegister() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_REGISTER_KEY);
            register =  Boolean.valueOf(val);

            return register;
        }

        public boolean isLogin_show() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_LOGIN_SHOW_KEY);
            login_show = Boolean.valueOf(val);

            return login_show;
        }

        public boolean isPost() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_POST_KEY);
            post = Boolean.valueOf(val);

            return post;
        }

        public boolean isComment() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_KEY);
            comment = Boolean.valueOf(val);

            return comment;
        }

        public boolean isCommentAllowAnonymous() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY);
            commentAllowAnonymous = Boolean.valueOf(val);

            return commentAllowAnonymous;
        }

    }

    /**
     *  Setting
     */
    @ToString
    public static class Setting {
        /**
         * 用户默认头像 "site.settings.userAvatar"
         */
        @Setter
        private String userAvatar;

        public String getUserAvatar() {
            userAvatar = DynamicProperties.staticProperties.getProperty(SITE_SETTINGS_USERAVATAR_KEY);

            return userAvatar;
        }

    }
}
