package com.mtons.mblog.dao.mybatis.shardingsphere.algorithm;

import com.google.common.collect.Lists;
import com.mtons.mblog.dao.mybatis.shardingsphere.enums.ModShardingRouteConfigType;
import com.mtons.mblog.dao.mybatis.shardingsphere.model.DefaultShardingConfigInfoModel;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author ycs
 * @Date 2019/5/13 10:39 AM
 * @since 1.0.0
 */
@Slf4j
@Component
public class  ModShardingAlgorithm implements PreciseShardingAlgorithm {

    /**
     * 得到目标表
     * @param routeConfigType
     * @param shardingValue
     * @return
     */
    public String getActualTable(ModShardingRouteConfigType routeConfigType, String shardingValue) {
        List<DefaultShardingConfigInfoModel> shardingGroupList = Lists.newArrayList();
        long value = Long.parseLong(shardingValue);

        final long mod = value % routeConfigType.getMod();

        return String.format("%s_%d", ModShardingRouteConfigType.getLogicTableName(routeConfigType.getBaseEntryClazz()), mod);
    }


    /**
     * 得到所有分表数据
     * @param routeConfigType
     * @return
     */
    public String getActualDataNodes(ModShardingRouteConfigType routeConfigType) {
        List<String>  dataNode = Lists.newArrayList();
        for(int i = 0; i < routeConfigType.getMod(); i++) {
            dataNode.add(String.format("dataSource.%s_%d", ModShardingRouteConfigType.getLogicTableName(routeConfigType.getBaseEntryClazz()),  i));
        }
        return StringUtils.join(dataNode, ",");
    }


    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue preciseShardingValue) {
        ModShardingRouteConfigType routeConfigType = ModShardingRouteConfigType.getByLogicTable(preciseShardingValue.getLogicTableName());
        Object objVal = preciseShardingValue.getValue();
        String table  = getActualTable(routeConfigType, objVal.toString());

        log.info("shardingValue:{},target table:{}", objVal, table);

        if(availableTargetNames.contains(table)) {
            return table;
        }

        throw new RuntimeException();
    }

    public static void main(String[] args) {
        System.out.println(Long.parseLong("1135474099322093650") % 32);
        System.out.println(Long.parseLong("6693") % 32);
    }

}
