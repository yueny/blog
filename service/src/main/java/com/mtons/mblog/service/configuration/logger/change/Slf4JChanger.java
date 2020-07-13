package com.mtons.mblog.service.configuration.logger.change;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

/**
 * slf4J 日志级别操作服务
 */
public class Slf4JChanger extends AbstractChanger {
    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Override
    public String getLevel(String packageName){
        Logger logger = loggerContext.getLogger(packageName);
//        ArrayUtil.hasNull(logger,logger.getLevel());//依赖Hutool工具
        return hasNull(logger,logger.getLevel())
                ? ""
                : logger.getLevel().toString();
    }

    @Override
    public void setLevel(String packageName,String level){
        loggerContext.getLogger(packageName).setLevel(Level.toLevel(level));
    }

}
