package com.mtons.mblog.dao.mybatis.plus.shardingsphere.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 默认分片配置信息
 * @author ycs
 * @Date 2019/5/9 7:51 PM
 * @since 1.0.0
 */
@Getter
@Setter
public class DefaultShardingConfigInfoModel {

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 基础表名
     */
    private String tableName;

    /**
     * 所在组存储的最大值
     */
    private long maxValue;

    /**
     * 所在组存储的最大值
     */
    private long minValue;

    /**
     * hash取模值（可理解为此组的分片数）
     */
    private int hashKey;
}
