package com.mtons.mblog.service.ability;

import com.mtons.mblog.service.ability.xml.UploadConfigModelData;

/**
 * xml 配置文件
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 17:39
 */
public interface IUploadXmlAbilityService {
    /**
     * xml 配置文件实体
     *
     * @return
     */
    UploadConfigModelData getConfigModelData();
}
