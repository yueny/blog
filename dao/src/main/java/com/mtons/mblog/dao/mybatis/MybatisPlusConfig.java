package com.mtons.mblog.dao.mybatis;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.mtons.mblog.dao.mybatis.interceptor.IgnoreCreateDateFieldInterceptor;
import com.mtons.mblog.dao.mybatis.interceptor.PerformanceInterceptorX;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:09
 *
 */
@Configuration
@MapperScan(basePackages = {"com.mtons.mblog.dao.mapper"},
        sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class MybatisPlusConfig extends AbstractMybatisPlusConfig {

    private Long mybatisPlusSqlMaxTime = 200L;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean(name="mybatisSqlSessionFactory")
    @Primary
    public SqlSessionFactory mybatisSqlSessionFactory(GlobalConfig globalConfig,
                                               PaginationInterceptor paginationInterceptor,
                                               PerformanceInterceptorX performanceInterceptorX,
                                               IgnoreCreateDateFieldInterceptor commonFieldInterceptor)
            throws Exception {
        return getAssemblySqlSessionFactory(dataSource, globalConfig,
                paginationInterceptor, performanceInterceptorX, commonFieldInterceptor);
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig conf = new GlobalConfig();
        // 默认值：false 是否缓存 Sql 解析，默认不缓存
        conf.setSqlParserCache(true);

        // 数据库相关配置
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
//        // 逻辑删除值
//        dbConfig.setLogicDeleteValue(String.valueOf(DelEnum.DEL.getVal()));
//        // 逻辑未删除值
//        dbConfig.setLogicNotDeleteValue(String.valueOf(DelEnum.NORMAL.getVal()));
        conf.setDbConfig(dbConfig);

        return conf;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public PerformanceInterceptorX performanceInterceptor() {
        PerformanceInterceptorX performanceInterceptorX = new PerformanceInterceptorX();
        performanceInterceptorX.setFormat(true);
        performanceInterceptorX.setMaxTime(mybatisPlusSqlMaxTime);
        return performanceInterceptorX;
    }

    @Bean
    public IgnoreCreateDateFieldInterceptor commonFieldInterceptor(){
        return new IgnoreCreateDateFieldInterceptor();
    }
}
