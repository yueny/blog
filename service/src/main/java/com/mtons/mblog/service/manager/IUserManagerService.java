/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.manager;

import com.mtons.mblog.model.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 汇总用户信息、头像信息、权限角色信息和安全基本信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午12:05
 *
 */
public interface IUserManagerService {
    /**
     * 查询单个用户的所有信息
     * @param uid 用户uid
     * @return
     */
    UserVO get(String uid);

    /**
     * 分页查询
     * @param pageable
     * @param name
     */
    Page<UserVO> paging(Pageable pageable, String name);
}
