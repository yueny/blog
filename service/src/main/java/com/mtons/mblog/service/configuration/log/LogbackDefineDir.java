package com.mtons.mblog.service.configuration.log;

import ch.qos.logback.core.PropertyDefinerBase;
import com.mtons.mblog.service.comp.config.IConfigGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 在springboot中, 或者spring相关框架中, 可以通过实现
 * logback的PropertyDefinerBase 的方法
 * 来动态决定日志目录.
 */
@Component
public class LogbackDefineDir extends PropertyDefinerBase implements ILogbackDefineDir {
    @Autowired
    private IConfigGetService configGetService;

    @Override
    public String getPropertyValue() {
        return configGetService.getKey("logs.runtime.home");
    }
}
