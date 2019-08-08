package com.mtons.mblog.dao.mybatis.plus.shardingsphere.enums;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yueny.kapo.api.pojo.IEntry;
import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;
import org.apache.commons.digester.annotations.utils.AnnotationUtils;

/**
 * 取模分片路由配置
 *
 * @author ycs
 * @Date 2019/5/13 10:19 AM
 * @since 1.0.0
 */
public enum ModShardingRouteConfigType implements IEnumType {
//    /**
//     * 资产基础信息
//     */
//    ASSET_INFO(AssetInfo.class, 32, "asset_code"),
    ;

    public static int getMod(Class<? extends IEntry> baseEntryClazz) {
        for(ModShardingRouteConfigType tableRuleConfigType : values()) {
            if(tableRuleConfigType.getBaseEntryClazz().equals(baseEntryClazz)) {
                return tableRuleConfigType.getMod();
            }
        }

        throw new RuntimeException();
    }

    public static String getLogicTableName(Class<? extends IEntry> baseEntryClazz) {
        Object tableName = AnnotationUtils.getAnnotationValue(baseEntryClazz.getAnnotation(TableName.class));

        if(tableName == null) {
            throw new RuntimeException();
        }

        return tableName.toString();
    }

    public static String getShardingColumns(Class<? extends IEntry> baseEntryClazz) {
        for(ModShardingRouteConfigType tableRuleConfigType : values()) {
            if(tableRuleConfigType.getBaseEntryClazz().equals(baseEntryClazz)) {
                return tableRuleConfigType.getShardingColumns();
            }
        }

        throw new RuntimeException();
    }

    public static ModShardingRouteConfigType getByLogicTable(String logicTable) {
        for(ModShardingRouteConfigType tableRuleConfigType : values()) {
            String lt = getLogicTableName(tableRuleConfigType.getBaseEntryClazz());
            if(lt.equals(logicTable)) {
                return tableRuleConfigType;
            }
        }
        throw new RuntimeException();
    }


    /**
     * 数据库实体
     */
    @Getter
    private Class<? extends IEntry> baseEntryClazz;

    /**
     * 分片取模值
     */
    @Getter
    private int mod;

    /**
     * 分片列
     */
    @Getter
    private String shardingColumns;

    ModShardingRouteConfigType(Class<? extends IEntry> baseEntryClazz, int mod, String shardingColumns) {
        this.baseEntryClazz = baseEntryClazz;
        this.mod = mod;
        this.shardingColumns = shardingColumns;
    }

//    public static void main(String[] args) {
//        for(ModShardingRouteConfigType modShardingRouteConfigType : values()) {
//            System.out.println(String.format("表名:%s,分片列名:%s, 模:%s", getLogicTableName(modShardingRouteConfigType.getBaseEntryClazz())
//            ,modShardingRouteConfigType.getShardingColumns(),
//                    modShardingRouteConfigType.getMod()));
//        }
//    }
}
