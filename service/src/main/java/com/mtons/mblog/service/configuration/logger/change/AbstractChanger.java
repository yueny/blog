package com.mtons.mblog.service.configuration.logger.change;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractChanger implements IChanger {

    /**
     * 日志等级常量
     */
    //    final String[] levels = {"ERROR","WARN","INFO","DEBUG","TRACE"};
    private static final String[] levels = {"OFF","FATAL","ERROR","WARN","INFO","DEBUG","TRACE","ALL"};

    /**
     * 获取指定包日志级别             封装[设置日志级别+封装返回值信息]
     * @param packageName           包名
     * @return String               日志级别信息
     */
    @Override
    public String getLogger(String packageName){
        return  packageName + "日志等级为:" + getLevel(packageName);
    }

    /**
     * 设置指定包日志级别             封装[日志级别检测+设置日志级别+封装返回值信息]
     * @param packageName           包名
     * @return String               日志级别信息
     */
    @Override
    public String setLogger(String packageName,String level){
        boolean isAllowed = isAllowed(level);
        if (isAllowed){
            setLevel(packageName,level);
        }
        return isAllowed
                ? packageName+"日志等级更改为:"+level
                : packageName+"日志等级修改失败,可用值[ERROR,WARN,INFO,DEBUG,TRACE]";
    }

    /**
     * 判断是否是合法的日志级别
     * @param level                 日志等级
     * @return boolean
     */
    public static boolean isAllowed(String level){
        return Arrays.asList(levels).contains(level.toUpperCase());
    }

    /**
     * 判断多个对象中是否包含空对象
     * @param objects               多个对象
     * @return String               日志级别
     */
    protected boolean hasNull(Object... objects) {
        if (Objects.nonNull(objects)) {
            for (Object element : objects) {
                if (null == element) {
                    return true;
                }
            }
        }
        return false;
    }

}
