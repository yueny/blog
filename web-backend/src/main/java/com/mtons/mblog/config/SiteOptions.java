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

import com.mtons.mblog.config.options.SiteConfigOption;
import com.mtons.mblog.config.xml.UploadConfigUtil;
import com.mtons.mblog.service.comp.configure.IConfigureConstant;
import com.mtons.mblog.service.comp.configure.impl.ConfigureGetService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
     * 系统版本号
     */
    @Getter
    @Setter
    @Value("${"+ IConfigureConstant.SITE_VERSION_KEY +"}")
    private String version;

    /**
     * 运行文件存储路径。
     * 配置由[application.yml] site:location: ${user.dir} 加载
     */
    @Setter
    private String location;

    /**
     * 控制器配置。
     * 配置由配置中心 配置项 site.controls.* 加载 和 [application.yml] site:controls: 进行controls对象实例化
     */
    @Getter
    private Controls controls = new Controls();

    /**
     * 属性配置。
     * 配置由[application.yml] site.options.* 加载
     * 以及 ContextStartup中读取数据库配置通过map.put增加配置项
     */
    @Getter
    @Setter
    private Map<String, String> options = new HashMap<>();

    /**
     * 默认值配置。
     * 配置由配置中心 配置项 site.settings.* 加载
     */
    // TODO 此处取值为null， ConfigurationProperties 未生效，需要确定原因
    @Getter
    @Deprecated
    private Settings settings;

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getLocation() {
        return UploadConfigUtil.getUploadConfig().getLocation();
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

    @ToString
    public static class Controls extends SiteConfigOption.Control {
        @Setter
        private boolean register_email_validate;

        public boolean isRegister_email_validate() {
            String val = ConfigureGetService.get(IConfigureConstant.SITE_CONTROLS_REGISTER_EMAIL_VALIDATE_KEY);
            register_email_validate =  Boolean.valueOf(val);

            return register_email_validate;
        }
    }

    @ToString
    public static class Settings extends SiteConfigOption.Setting {
        //.
    }

}
