/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.configuration.site;

import com.mtons.mblog.service.ability.IUploadXmlAbilityService;
import com.mtons.mblog.service.core.ConfigKeysConstant;
import com.mtons.mblog.service.ability.impl.DiamondConfigAbilityServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 读取 yml 配置信息， 该服务不允许直接调用
 *
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/01/18
 */
@Configuration
@ConfigurationProperties(prefix = "site")
//@RefreshScope
public class SiteOptionsConfiguration extends AbstractSiteConfigOption {
    @Autowired
    private IUploadXmlAbilityService uploadXmlAbilityService;

    /**
     * 系统版本号
     */
    @Getter
    @Setter
    @Value("${"+ ConfigKeysConstant.SITE_VERSION_KEY +"}")
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
   * 属性配置。 配置由[application.yml] site.options.* 加载 以及 ContextStartup中读取数据库配置通过map.put增加配置项
   *
   * <pre>
   *         Map<String, String> map = siteOptions.getOptions();
   *         options.forEach(opt -> {
   *             if (StringUtils.isNoneBlank(opt.getKey(), opt.getValue())) {
   *                 map.put(opt.getKey(), opt.getValue());
   *             }
   *         });
   * </pre>
   */
  @Getter @Setter private Map<String, String> options = new HashMap<>();

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
        return uploadXmlAbilityService.getConfigModelData().getLocation();
    }
    public String getLocationUri() {
        return uploadXmlAbilityService.getConfigModelData().getLocationUri();
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
    public static class Controls extends AbstractSiteConfigOption.Control {
        @Setter
        private boolean register_email_validate;

        public boolean isRegister_email_validate() {
            String val = DiamondConfigAbilityServiceImpl.get(ConfigKeysConstant.SITE_CONTROLS_REGISTER_EMAIL_VALIDATE_KEY);
            register_email_validate =  Boolean.valueOf(val);

            return register_email_validate;
        }
    }

    @ToString
    public static class Settings extends AbstractSiteConfigOption.Setting {
        //.
    }

}
