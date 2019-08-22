package com.mtons.mblog.dao.mybatis.plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mtons.mblog.dao.mybatis.MapperConst;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Calendar;
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
        Date now = Calendar.getInstance().getTime();

        // 在插入的时候自动填充 createDate
        this.setFieldValByName(MapperConst.CREATE_TIME_FIELD, now, metaObject);
        this.setFieldValByName(MapperConst.MODIFY_TIME_FIELD, now, metaObject);

        // 如果字段不存在， 也无影响
        this.setFieldValByName(MapperConst.VERSION_FIELD, 1, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 在插入的时候自动更新 modifyTime
        this.setFieldValByName(MapperConst.MODIFY_TIME_FIELD, Calendar.getInstance().getTime(), metaObject);// 不允许手动设置更新时间

        // @Version 会实现 +1 操作
//        Object version = this.getFieldValByName(MapperConst.VERSION_FIELD, metaObject);
//        if(null != version){
//            this.setFieldValByName(MapperConst.VERSION_FIELD, (Integer)version+1, metaObject);
//        }
    }
}
