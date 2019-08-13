package com.mtons.mblog.util;

import com.yueny.superclub.api.pojo.IBo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param clazzInstance
     *            clazz 的new实例
     * @param clazz
     *            clazz = clazzInstance.getClass()
     * @param primaryKey
     *            主键的值
     */
    public static void setPrimaryKey(Object clazzInstance, final Class<? extends IBo> clazz, final long primaryKey) {
        final Field primaryKeyField = extractPrimaryKeyField(clazz);
        if(primaryKeyField == null){
            return;
        }

        final boolean isAccessible = primaryKeyField.isAccessible();
        primaryKeyField.setAccessible(true);
        try {
            primaryKeyField.set(clazzInstance, primaryKey);
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

        List<Field> fieldList = get(clazz);
        // 取默认值
        for (final Field field : fieldList) {
            if (StringUtils.equals(field.getName(), DEFAULT_PK_FIELD_NAME)) {
                PK_FIELDS.put(clazz, field);
                return field;
            }
        }

        // 都取不到则报错
        return null;
    }

    /**
     * 获取类的所有Field
     * @param entryClass
     * @return
     */
    private static List<Field> get(Class<? extends IBo> entryClass){
        List<Field> fieldList = new ArrayList<>();

        Class clazz = entryClass;
        do {
            Field[] fields = clazz.getDeclaredFields();

            for(int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                //if (this.type != this.object ^ Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }

            clazz = clazz.getSuperclass();
//		} while(clazz != null);
        } while(clazz != null);

        return fieldList;
    }

}
