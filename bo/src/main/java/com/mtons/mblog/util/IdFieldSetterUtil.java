package com.mtons.mblog.util;

import com.yueny.superclub.api.pojo.IBo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/** bo业务实体主键赋值工具
 *
 * 参考资料 com.yueny.kapo.api.util.PrimaryKeyFieldUtil
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-08 10:50
 */
public class IdFieldSetterUtil {
    private static final Map<Class<? extends IBo>, Field> PK_FIELDS = new HashMap<>();
    /**
     * 主键默认的 field 名。
     */
    private static final String DEFAULT_PK_FIELD_NAME = "id";

    /**
     * 设置主键
     *
     * @param primaryKey
     *            主键
     */
    public static void setPrimaryKey(final Class<? extends IBo> clazz, final long primaryKey) {
        final Field primaryKeyField = extractPrimaryKeyField(clazz);
        if(primaryKeyField == null){
            return;
        }

        final boolean isAccessible = primaryKeyField.isAccessible();
        primaryKeyField.setAccessible(true);
        try {
            primaryKeyField.set(clazz, primaryKey);
        } catch (final IllegalAccessException e) {
            System.err.println("无法获取实体" + clazz.getName() + "主键");
        } finally {
            primaryKeyField.setAccessible(isAccessible);
        }
    }

    /**
     * 获取主键域
     *
     * @param clazz
     *            实体类型
     */
    private static Field extractPrimaryKeyField(final Class<? extends IBo> clazz) {
        if (PK_FIELDS.containsKey(clazz)) {
            return PK_FIELDS.get(clazz);
        }

        // 取默认值
        for (final Field field : clazz.getDeclaredFields()) {
            if (StringUtils.equals(field.getName(), DEFAULT_PK_FIELD_NAME)) {
                PK_FIELDS.put(clazz, field);
                return field;
            }
        }

        // 都取不到则报错
        return null;
    }

}
