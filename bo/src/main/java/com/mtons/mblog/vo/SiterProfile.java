/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.vo;

import com.yueny.superclub.api.pojo.IBo;
import lombok.*;

import java.io.Serializable;

/**
 * 网站配置信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiterProfile implements IBo, Serializable {
    /**
     * 是否允许匿名评论开关, true 为允许匿名评论
     */
    private boolean commentAllowAnonymous;

    /**
     * 系统版本号
     */
    private String version;

    /**
     * 用户默认头像路径或地址
     */
    private String userDefaultAvatar;

}
