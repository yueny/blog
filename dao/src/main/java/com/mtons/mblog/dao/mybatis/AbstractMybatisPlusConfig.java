package com.mtons.mblog.dao.mybatis;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.handlers.EnumAnnotationTypeHandler;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mtons.mblog.dao.mybatis.handler.MysqlMetaObjectHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * 抽象服务
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/10 上午10:48
 *
 */
public abstract class AbstractMybatisPlusConfig {
    protected SqlSessionFactory getAssemblySqlSessionFactory(DataSource dataSource,
                                                             GlobalConfig globalConfig,
                                                             Interceptor... interceptors)
            throws Exception {

        MybatisSqlSessionFactoryBean sqlSessionFactory = new MyMybatisSqlSessionFactoryBean();
        /* 数据源 */
        sqlSessionFactory.setDataSource(dataSource);
        /* 枚举扫描路径定义 */
        sqlSessionFactory.setTypeEnumsPackage(MapperConst.TYPE_ENUMS_PACKAGE);

        /* entity扫描,mybatis的Alias功能 */
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        /* 驼峰转下划线 */
        configuration.setMapUnderscoreToCamelCase(true);

        /* 默认添加 乐观锁插件 */
        configuration.addInterceptor(new OptimisticLockerInterceptor());

        if(interceptors != null){
            for (Interceptor interceptor: interceptors) {
                configuration.addInterceptor(interceptor);
            }
//        /* 插件 */
//         paginationInterceptor
//         commonFieldInterceptor
//         performanceInterceptorX
        }

        sqlSessionFactory.setConfiguration(configuration);

        /* 自动填充插件 */
        globalConfig.setMetaObjectHandler(new MysqlMetaObjectHandler());
        sqlSessionFactory.setGlobalConfig(globalConfig);

        /* SQL 逻辑删除注入器 */
        //globalConfig.setSqlInjector(new LogicSqlInjector());

        return sqlSessionFactory.getObject();
    }

    public static class MyMybatisSqlSessionFactoryBean extends MybatisSqlSessionFactoryBean {
        /**
         * @param clazz
         */
        protected Class<?> dealEnumType1(Class<?> clazz) {
            if (clazz.isEnum()) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    if (f.getAnnotation(EnumValue.class) == null ||
                            f.getAnnotation(com.yueny.superclub.api.annnotation.EnumValue.class) == null) {
                        continue;
                    }

                    f.setAccessible(true);
                    EnumAnnotationTypeHandler.addEnumType(clazz, f);
                    return clazz;
                }
            }
            return null;
        }

        /**
         * 对原生枚举的处理类，默认{@link EnumOrdinalTypeHandler}
         *
         * @param typeHandlerRegistry
         * @param enumClazz
         */
        @Override
        protected void registerOriginalEnumTypeHandler(TypeHandlerRegistry typeHandlerRegistry, Class<?> enumClazz) {
            Class<?> aClass = dealEnumType1(enumClazz);
            if (aClass == null) {
                super.registerOriginalEnumTypeHandler(typeHandlerRegistry, enumClazz);
            } else {
                typeHandlerRegistry.register(enumClazz, MyEnumTypeHandler.class);
            }
        }

    }

    @MappedJdbcTypes(value = {JdbcType.INTEGER,JdbcType.TINYINT,JdbcType.SMALLINT},includeNullJdbcType = true)
    public static class MyEnumTypeHandler<E extends Enum<E>> extends EnumAnnotationTypeHandler<E> {
        public MyEnumTypeHandler(Class<E> type) {
            super(type);
        }
    }
}
