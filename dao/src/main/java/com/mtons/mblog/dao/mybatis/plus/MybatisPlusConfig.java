package com.mtons.mblog.dao.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mtons.mblog.dao.mybatis.plus.AbstractMybatisPlusConfig;
import com.mtons.mblog.dao.mybatis.plus.handler.MysqlMetaObjectHandler;
import com.mtons.mblog.dao.mybatis.plus.interceptor.IgnoreCreateDateFieldInterceptor;
import com.mtons.mblog.dao.mybatis.plus.interceptor.PerformanceInterceptorX;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactory(GlobalConfig globalConfig,
                 MybatisConfiguration mybatisConfig,
                 PaginationInterceptor paginationInterceptor,
                 PerformanceInterceptorX performanceInterceptorX,
                 IgnoreCreateDateFieldInterceptor commonFieldInterceptor,
                 OptimisticLockerInterceptor optimisticLockerInterceptor)
            throws Exception {
        return getAssemblySqlSessionFactory(dataSource, mybatisConfig, globalConfig,
                paginationInterceptor, performanceInterceptorX, commonFieldInterceptor,
                optimisticLockerInterceptor);
    }

    /**
     * 定义 MP 全局策略
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 是否控制台 print mybatis-plus 的 LOGO
        globalConfig.setBanner(true);
        /* 自动填充插件 */
        globalConfig.setMetaObjectHandler(new MysqlMetaObjectHandler());

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 表名下划线命名默认true
        dbConfig.setTableUnderline(true);
        // id类型
        dbConfig.setIdType(IdType.AUTO);
//        // 逻辑已删除值
//        dbConfig.setLogicDeleteValue(String.valueOf(DelEnum.DEL.getValue()));
        //        // 逻辑未删除值
//        dbConfig.setLogicNotDeleteValue(String.valueOf(DelEnum.ACTIVE.getValue()));

        globalConfig.setDbConfig(dbConfig);

        return globalConfig;
    }

    /**
     * 乐观锁插件， 配合 @Version    Integer version 使用
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        paginationInterceptor.setDbType(DbType.MYSQL);

        List<ISqlParser> sqlParserList = new ArrayList<>();
//        // 攻击 SQL 阻断解析器、加入解析链, 阻止恶意的全表更新删除
//        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
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
