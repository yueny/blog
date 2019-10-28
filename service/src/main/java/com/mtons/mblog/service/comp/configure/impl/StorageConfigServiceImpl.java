package com.mtons.mblog.service.comp.configure.impl;

import com.mtons.mblog.base.consts.OptionsKeysConsts;
import com.mtons.mblog.bo.OptionsBo;
import com.mtons.mblog.service.atom.bao.OptionsService;
import com.mtons.mblog.service.comp.configure.IStorageConfigService;
import com.mtons.mblog.service.comp.configure.IUploadConfigConfig;
import com.mtons.mblog.service.storage.NailPathData;
import com.mtons.mblog.service.util.file.FilePathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 20:03
 */
@Component
public class StorageConfigServiceImpl implements IStorageConfigService {
    @Autowired
    private IUploadConfigConfig uploadConfigConfig;
    @Autowired
    private OptionsService optionsService;


    private static final String image_server_uri   = OptionsKeysConsts.IMAGE_SERVER_URI;
    private static final String image_server_location = OptionsKeysConsts.IMAGE_SERVER_LOCATION;

    @Override
    public String getLocation() {
        OptionsBo optionsBo = optionsService.findByKey(image_server_location);
        if(optionsBo == null || StringUtils.isEmpty(optionsBo.getValue())){
            // 配置项不存在，则返回本地配置
            return uploadConfigConfig.getConfigModelData().getLocation();
        }

        return optionsBo.getValue().trim();
    }

    @Override
    public String getLocationUri() {
        OptionsBo optionsBo = optionsService.findByKey(image_server_uri);
        if(optionsBo == null || StringUtils.isEmpty(optionsBo.getValue())){
            // 配置项不存在，则返回 空字符串
            return "";
        }

        return optionsBo.getValue().trim();
    }

    @Override
    public String getWholePathName(NailPathData nailPath, String md5) {
        String path = FilePathUtils.wholePathName(nailPath.get(), nailPath.getOriginalFilename(), md5);

        return path;
    }

}
