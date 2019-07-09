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

import com.mtons.mblog.service.comp.IConfiguterGetService;
import com.mtons.mblog.service.comp.impl.ConfiguterGetService;
import lombok.Setter;
import lombok.ToString;

/**
 * 读取配置中心配置信息
 */
@ToString
public abstract class SiteConfigOption {
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
            String val = ConfiguterGetService.get(IConfiguterGetService.SITE_CONTROLS_REGISTER_KEY);
            register =  Boolean.valueOf(val);

            return register;
        }

        public boolean isLogin_show() {
            String val = ConfiguterGetService.get(IConfiguterGetService.SITE_CONTROLS_LOGIN_SHOW_KEY);
            login_show = Boolean.valueOf(val);

            return login_show;
        }

        public boolean isPost() {
            String val = ConfiguterGetService.get(IConfiguterGetService.SITE_CONTROLS_POST_KEY);
            post = Boolean.valueOf(val);

            return post;
        }

        public boolean isComment() {
            String val = ConfiguterGetService.get(IConfiguterGetService.SITE_CONTROLS_COMMENT_KEY);
            comment = Boolean.valueOf(val);

            return comment;
        }

        public boolean isCommentAllowAnonymous() {
            String val = ConfiguterGetService.get(IConfiguterGetService.SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY);
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
            userAvatar = ConfiguterGetService.get(IConfiguterGetService.SITE_SETTINGS_USERAVATAR_KEY);

            return userAvatar;
        }

    }
}
