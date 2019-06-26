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

import com.mtons.mblog.config.xml.UploadConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
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
public class SiteOptions extends SiteConfigOption {
    /**
     * 运行文件存储路径
     */
    private String location;

    /**
     * 控制器配置
     */
    @Getter
    private Controls controls;

    /**
     * 属性配置
     */
    @Getter
    private Map<String, String> options = new HashMap<>();

    public String getLocation() {
        return UploadConfigUtil.getUploadConfig().getLocation();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
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
        //@NotNull
        String value = getValue(key);

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

    public static class Controls extends Control {
        @Setter
        private boolean register_email_validate;

        public boolean isRegister_email_validate() {
            return register_email_validate;
        }
    }

}
