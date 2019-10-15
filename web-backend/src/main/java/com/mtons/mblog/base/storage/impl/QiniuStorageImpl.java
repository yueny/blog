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

import com.mtons.mblog.base.consts.OptionsKeysConsts;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.service.comp.storage.Storage;
import com.mtons.mblog.service.util.file.FileKit;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class QiniuStorageImpl extends AbstractStorage implements Storage {
    private static final String oss_bucket = OptionsKeysConsts.QINIU_OSS_BUCKET;
    private static final String oss_domain = OptionsKeysConsts.QINIU_OSS_DOMAIN;
    private static final String oss_key    = OptionsKeysConsts.QINIU_OSS_KEY;
    private static final String oss_secret = OptionsKeysConsts.QINIU_OSS_SECRET;
    private static final String oss_src    = OptionsKeysConsts.QINIU_OSS_SRC;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String accessKey = options.getValue(oss_key);
        String secretKey = options.getValue(oss_secret);
        String domain = options.getValue(oss_domain);
        String bucket = options.getValue(oss_bucket);
        String src = options.getValue(oss_src);

        if (StringUtils.isAnyBlank(accessKey, secretKey, domain, bucket)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }

        if (StringUtils.isNotBlank(src)) {
            if (src.startsWith("/")) {
                src = src.substring(1);
            }

            if (!src.endsWith("/")) {
                src = src + "/";
            }
        } else {
            src = "";
        }

        String key = UpYunUtils.md5(bytes);
        String path = src + key + FileKit.getSuffix(pathAndFileName);

        Zone z = Zone.autoZone();
        Configuration configuration = new Configuration(z);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, path);

        UploadManager uploadManager = new UploadManager(configuration);
        Response response = uploadManager.put(bytes, path, upToken);

        if (!response.isOK()) {
            throw new MtonsException(response.bodyString());
        }
        return domain.trim() + "/" + path;
    }

    @Override
    public void deleteFile(String storePath) {
        String accessKey = options.getValue(oss_key);
        String secretKey = options.getValue(oss_secret);
        String domain = options.getValue(oss_domain);
        String bucket = options.getValue(oss_bucket);

        if (StringUtils.isAnyBlank(accessKey, secretKey, domain, bucket)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }

        String path = StringUtils.remove(storePath, domain.trim());

        Zone z = Zone.autoZone();
        Configuration configuration = new Configuration(z);
        Auth auth = Auth.create(accessKey, secretKey);

        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(bucket, path);
        } catch (QiniuException e) {
            Response r = e.response;
            log.error(e.getMessage(), r.toString());
        }
    }

}
