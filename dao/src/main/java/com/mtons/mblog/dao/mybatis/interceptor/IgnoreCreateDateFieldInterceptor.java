package com.mtons.mblog.dao.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.mtons.mblog.dao.mybatis.MapperConst;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Properties;

/**
 * 更新时忽略createDate.
 *
 * <pre>
 *    数据库表字段create_date在插入记录之后不允许修改
 *    mybatis-plus的MetaObjectHandler执行在statement生成之后，无法满足需求，使用mybatis-interceptor解决
 *    在数据库更新时忽略createDate值
 * </pre>
 *
 * @Date 2019/4/16 3:54 PM
 * @since 1.0.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class IgnoreCreateDateFieldInterceptor extends AbstractSqlParserHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getArgs()[1]);
        setFieldValByName(MapperConst.CREATE_TIME_FIELD, null, metaObject);
        return invocation.proceed();
    }

    private void setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName)) {
            metaObject.setValue(fieldName, fieldVal);
        } else if (metaObject.hasGetter(Constants.ENTITY)) {
            Object et = metaObject.getValue(Constants.ENTITY);
            if (et != null) {
                MetaObject etMeta = SystemMetaObject.forObject(et);
                if (etMeta.hasSetter(fieldName)) {
                    etMeta.setValue(fieldName, fieldVal);
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

