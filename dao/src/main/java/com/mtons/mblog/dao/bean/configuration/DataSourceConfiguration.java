package com.mtons.mblog.dao.bean.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置
 */
@Configuration
@Slf4j
public class DataSourceConfiguration {

//    @Value("${db.driverClass}")
//    private String driverClassName;

    @Value("${db.mysql.url}")
    private String url;

    @Value("${db.mysql.username}")
    private String username;
    @Value("${db.mysql.password}")
    private String password;

    @Value("${db.mysql.default.pool.initialSize}")
    private Integer initialSize;
    @Value("${db.mysql.default.pool.minIdle}")
    private Integer minIdle;
    @Value("${db.mysql.default.pool.maxActive}")
    private Integer maxActive;
    @Value("${db.mysql.default.pool.maxWait}")
    private Integer maxWait;
    @Value("${db.mysql.default.pool.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${db.mysql.default.pool.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

    /**
     * 打开PSCache，并且指定每个连接上PSCache的大小
     */
    @Value("${db.mysql.poolPreparedStatements}")
    private Boolean poolPreparedStatements;

    @Bean(value = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        dataSource.setValidationQuery("SELECT 'x' from dual");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);

        dataSource.setFilters("stat, wall");

        dataSource.setUseGlobalDataSourceStat(true);

        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
