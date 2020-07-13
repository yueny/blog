package com.mtons.mblog.service.configuration.logger.change;

/**
 * 日志信息改变服务
 */
public interface IChanger {
    /**
     * 获取指定包日志级别             封装[设置日志级别+封装返回值信息]
     * @param packageName           包名
     * @return String               日志级别信息
     */
    String getLogger(String packageName);

    /**
     * 设置指定包日志级别             封装[日志级别检测+设置日志级别+封装返回值信息]
     * @param packageName           包名
     * @return String               日志级别信息
     */
    String setLogger(String packageName,String level);

    /**
     * 获取制定包的日志级别
     * @param packageName           包名
     * @return String               日志级别
     */
    String getLevel(String packageName);

    /**
     * 设置制定包的日志级别
     * @param packageName           包名
     * @param level                 日志等级
     */
    void setLevel(String packageName, String level);

}
