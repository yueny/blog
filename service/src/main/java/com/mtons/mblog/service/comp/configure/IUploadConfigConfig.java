package com.mtons.mblog.service.comp.configure;

import com.mtons.mblog.service.config.options.xml.UploadConfigModelData;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 17:39
 */
public interface IUploadConfigConfig {
    /**
     * xml 配置文件实体
     *
     * @return
     */
    UploadConfigModelData getConfigModelData();
}
