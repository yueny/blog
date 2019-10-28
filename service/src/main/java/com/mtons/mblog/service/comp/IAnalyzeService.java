package com.mtons.mblog.service.comp;

/**
 * 分析服务
 *
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 13:19
 */
public interface IAnalyzeService {
    /**
     * 是否为攻击地址/地址是否存在被攻击行为
     *
     * @param resourcePath 访问资源地址
     * @return true 为被攻击
     */
    boolean isAttackUrl(String resourcePath);
}
