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

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtons.mblog.bo.UserSecurityBO;
import com.mtons.mblog.entity.UserSecurityEntry;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:05
 *
 */
public interface IUserSecurityService extends IService<UserSecurityEntry> {
    /**
     * 数据保存。 如果数据存在则更新
     *
     * @param bo 实体对象
     */
    boolean save(UserSecurityBO bo);

    /**
     * 根据uid获取对象
     */
    UserSecurityBO getByUid(String uid);
}
