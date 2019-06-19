package com.mtons.mblog.config;

import com.yueny.superclub.util.strategy.spring.ManageSpringBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统信息配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午1:53
 *
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    public ManageSpringBeans springManagerBeans() {
        return new ManageSpringBeans();
    }

//    /**
//     * 动态改变日志级别
//     */
//    @Bean
//    public ApolloLog4jConfigBean polloLog4jConfigBean() {
//        ApolloLog4jConfigBean log4jConfigBean = new ApolloLog4jConfigBean();
//        log4jConfigBean.setDefaultNamespace(LOG_APOLLO_NAMESPACE);
//
//        return log4jConfigBean;
//    }

}
