package com.mtons.mblog.dao.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mtons.mblog.dao.mybatis.MapperConst;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 公共字段填充
 * @author ycs
 * @Date 2019/4/16 3:57 PM
 * @since 1.0.0
 */
public class MysqlMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        // 在插入的时候自动填充 createDate
        this.setFieldValByName(MapperConst.CREATE_TIME_FIELD, new Date(), metaObject);
        //this.setFieldValByName(MapperConst.MODIFY_TIME_FIELD, new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 在插入的时候自动更新 modifyTime
        //this.setFieldValByName(MapperConst.MODIFY_TIME_FIELD, new Date(), metaObject);// 不允许手动设置更新时间
    }
}
