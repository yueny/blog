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

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.taobao.diamond.extend.DynamicProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取 yml 配置信息
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/01/18
 */
@Configuration
@ConfigurationProperties(prefix = "site")
//@RefreshScope
public class SiteOptions {
    private static final String SITE_VERSION_KEY = "site.version";
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

    //    site.controls.register_email_validate=false

    /**
     * 系统版本号
     */
//    @Value("${site.version}")
    private String version;

    /**
     * 运行文件存储路径
     */
    private String location;

    /**
     * 控制器配置
     */
    private Controls controls;

    /**
     * 属性配置
     */
    private Map<String, String> options = new HashMap<>();

    public String getVersion() {
        return DynamicProperties.staticProperties.getProperty(SITE_VERSION_KEY);
//        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getValue(String key) {
        String value = options.get(key);
        return null != value ? value.trim() : null;
    }

    public Integer getIntegerValue(String key) {
        return Integer.parseInt(options.get(key));
    }

    public Integer[] getIntegerArrayValue(String key, String separator) {
        @NotNull String value = getValue(key);
        String[] array = value.split(separator);
        Integer[] ret = new Integer[array.length];
        for (int i = 0; i < array.length; i ++) {
            ret[i] = Integer.parseInt(array[i]);
        }
        return ret;
    }

    public boolean hasValue(String key) {
        return StringUtils.isNotBlank(options.get(key));
    }

    public static class Controls {
        @Setter
        private boolean register;
        @Setter
        private boolean register_email_validate;
        @Setter
        private boolean post;
        @Setter
        private boolean comment;

        @Setter
        private boolean login_show; // 是否显示注册按钮

        /* 是否允许匿名评论开关, true 为允许匿名评论 */
        @Setter
        private boolean commentAllowAnonymous;

        public boolean isRegister() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_REGISTER_KEY);
            return Boolean.valueOf(val);
//            return register;
        }

        public boolean isLogin_show() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_LOGIN_SHOW_KEY);
            return Boolean.valueOf(val);
//            return login_show;
        }

        public boolean isRegister_email_validate() {
            return register_email_validate;
        }

        public boolean isPost() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_POST_KEY);
            return Boolean.valueOf(val);
//            return post;
        }

        public boolean isComment() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_KEY);
            return Boolean.valueOf(val);
//            return comment;
        }

        public boolean isCommentAllowAnonymous() {
            String val = DynamicProperties.staticProperties.getProperty(SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY);
            return Boolean.valueOf(val);
        }

    }

}
