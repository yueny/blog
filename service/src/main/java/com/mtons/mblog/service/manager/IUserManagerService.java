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

import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.vo.RolePermissionVO;
import com.mtons.mblog.vo.UserVO;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    UserVO find(String uid);

    /**
     * 分页查询
     * @param pageable
     * @param name
     */
    Page<UserVO> paging(Pageable pageable, String name);

    /**
     * 注册服务
     * @param userBo
     */
    UserVO register(UserBO userBo);

    /**
     * 尝试登录动作
     * @param username 用户名
     * @param tryPassword 尝试的密码明文， 不代表真实密码
     *
     * @return 尝试密码的加密密码， 不校验与实际密码的正确性
     */
    String tryLogin(String username, String tryPassword);

    /**
     * 当前用户名和密文密码登陆动作， 成功登陆后记录最后一次登陆时间
     *
     * @param username
     * @param cryptPassword 密文密码
     * @return
     */
    UserBO getLogin(String username, String cryptPassword);

    /**
     * 查询用户已有的角色 和 权限清单
     * @param userId 用户ID
     * @return
     */
    List<RolePermissionVO> findListRolesByUserId(Long userId);
}
