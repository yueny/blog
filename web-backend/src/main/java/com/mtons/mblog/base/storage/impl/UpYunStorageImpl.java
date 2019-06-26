/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.storage.impl;

import com.UpYun;
import com.mtons.mblog.base.consts.OptionsKeysConsts;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.FileKit;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * created by langhsu
 * on 2019/1/20
 *
 * @since 3.0
 */
@Slf4j
@Component
public class UpYunStorageImpl extends AbstractStorage implements Storage {
    private static final String oss_bucket   = OptionsKeysConsts.UPYUN_OSS_BUCKET;
    private static final String oss_domain   = OptionsKeysConsts.UPYUN_OSS_DOMAIN;
    private static final String oss_operator = OptionsKeysConsts.UPYUN_OSS_OPERATOR;
    private static final String oss_password = OptionsKeysConsts.UPYUN_OSS_PASSWORD;
    private static final String oss_src      = OptionsKeysConsts.UPYUN_OSS_SRC;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String domain = options.getValue(oss_domain);
        String src = options.getValue(oss_src);

        if (StringUtils.isAnyBlank(domain)) {
            throw new MtonsException("请先在后台设置又拍云配置信息");
        }

        if (StringUtils.isBlank(src)) {
            src = "";
        } else {
            if (!src.startsWith("/")) {
                src = "/" + src;
            }

            if (!src.endsWith("/")) {
                src = src + "/";
            }
        }

        String key = UpYunUtils.md5(bytes);
        String path = src + key + FileKit.getSuffix(pathAndFileName);
        UpYun upYun = builder();
        upYun.setContentMD5(key);
        upYun.writeFile(path, bytes, true);
        return domain.trim() + path;
    }

    @Override
    public void deleteFile(String storePath) {
        String domain = options.getValue(oss_domain);
        String path = StringUtils.remove(storePath, domain.trim());
        UpYun yun = builder();
        try {
            yun.deleteFile(path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private UpYun builder() {
        String bucket = options.getValue(oss_bucket);
        String operator = options.getValue(oss_operator);
        String password = options.getValue(oss_password);

        if (StringUtils.isAnyBlank(bucket, operator, password)) {
            throw new MtonsException("请先在后台设置又拍云配置信息");
        }
        UpYun yun = new UpYun(bucket, operator, password);
        yun.setTimeout(60);
        yun.setApiDomain(UpYun.ED_AUTO);
        yun.setDebug(true);
        return yun;
    }
}
