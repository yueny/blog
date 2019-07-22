package com.mtons.mblog.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具
 */
public class LogUtil {
    // 慢服务日志。 如果需要.  "SLOWLY-PROFILE-LOGGER" 为 logger name
    private static final Logger slowlyProfileLog = LoggerFactory.getLogger("SLOWLY-PROFILE-LOGGER");

    // 服务统计日志。 "PROFILE-LOGGER" 为 logger name
    private static final Logger profileLog = LoggerFactory.getLogger("PROFILE-LOGGER");

    /**
     * 慢服务日志。 如果需要(只包含慢性能日志)
     */
    public static Logger getSlowlyProfileLog() {
        return slowlyProfileLog;
    }

    /**
     * 慢服务日志记录(只包含慢性能日志， 且会记录入 slowlyProfileLog 和 profileLog)
     */
    public static void slowlyProfileLogRecord(String format, Object... arguments) {
        getSlowlyProfileLog().info(format, arguments);

        profileLog(format, arguments);
    }

    /**
     * 服务统计日志(包含所有性能日志)
     */
    public static Logger getProfileLog() {
        return profileLog;
    }

    /**
     * 服务统计日志(包含所有性能日志)， 级别 info
     */
    public static void profileLog(String format, Object... arguments) {
        getProfileLog().info(format, arguments);
    }

    /**
     * 服务统计日志(包含所有性能日志)， 级别 debug
     */
    public static void profileLogDebug(String format, Object... arguments) {
        getProfileLog().debug(format, arguments);
    }
}
