package main.java.com.mtons.mblog.base.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtil {
    private static final Logger logger = LoggerFactory
            .getLogger("mblog");

    public static void info(String format, Object arguments){
        logger.info(format, arguments);
    }

    public static void info(String format, Object... arguments){
        logger.info(format, arguments);
    }

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public static void info(String msg, Throwable t){
        logger.info(msg, t);
    }

}
