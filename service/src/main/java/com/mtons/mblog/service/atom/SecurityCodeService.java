/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom;

/**
 * 验证码服务
 *
 * @author langhsu on 2015/8/14.
 */
public interface SecurityCodeService {
    /**
     * 生成验证码。 发送间隔时间不能少于1分钟
     * @param key
     * @param target : email mobile
     * @return
     */
    String generateCode(String key, int type, String target);

    /**
     * 检验验证码有效性
     * @param key
     * @param code
     * @return token
     */
    boolean verify(String key, int type, String code);
}
