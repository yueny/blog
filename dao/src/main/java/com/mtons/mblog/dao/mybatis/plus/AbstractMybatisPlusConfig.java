package com.mtons.mblog.dao.mybatis.plus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mtons.mblog.dao.mybatis.MapperConst;
import org.apache.ibatis.plugin.Interceptor;

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

}
