package com.mtons.mblog.dao.mybatis.plus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mtons.mblog.dao.mybatis.MapperConst;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 抽象服务
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/10 上午10:48
 *
 */
public abstract class AbstractMybatisPlusConfig {
    protected MybatisSqlSessionFactoryBean getAssemblySqlSessionFactory(DataSource dataSource,
                                                             MybatisConfiguration mybatisConfig,
                                                             GlobalConfig globalConfig,
                                                             Interceptor... interceptors)
            throws Exception {

        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        /* 数据源 */
        sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setTypeAliasesPackage("com.dal.model");
        /* 枚举扫描路径定义 */
        sqlSessionFactory.setTypeEnumsPackage(MapperConst.TYPE_ENUMS_PACKAGE);

        sqlSessionFactory.setConfiguration(mybatisConfig);

        // MP 全局配置注入
        sqlSessionFactory.setGlobalConfig(globalConfig);

        /* 拦截器 */
        sqlSessionFactory.setPlugins(interceptors);

        return sqlSessionFactory;
    }


    @Bean
    public MybatisConfiguration mybatisConfig() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        // 配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        // 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存，默认为 true
        mybatisConfiguration.setCacheEnabled(true);

        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        // 懒加载
        mybatisConfiguration.setAggressiveLazyLoading(true);
        // 如果查询结果中包含空值的列，则 MyBatis 在映射的时候，不会映射这个字段
        mybatisConfiguration.setCallSettersOnNulls(true);

        // 这个配置会将执行的sql打印出来，在开发或测试的时候可以用.
        mybatisConfiguration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        return mybatisConfiguration;
    }

}
