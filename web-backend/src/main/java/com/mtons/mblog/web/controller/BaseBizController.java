/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.service.config.SiteOptions;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.mtons.mblog.service.util.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller 业务基类
 */
public abstract class BaseBizController extends BaseController {
    @Autowired
    protected SiteOptions siteOptions;
    @Autowired
    private IUserManagerService userManagerService;


    /**
     * 获取登录信息
     *
     * @return
     */
    protected AccountProfile getProfile() {
        Subject subject = SecurityUtils.getSubject();
        return (AccountProfile) subject.getPrincipal();
    }

    protected void putProfile(AccountProfile profile) {
        SecurityUtils.getSubject().getSession(true).setAttribute("profile", profile);
    }

    protected boolean isAuthenticated() {
        return SecurityUtils.getSubject() != null && (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered());
    }

    protected PageRequest wrapPageable() {
        return wrapPageable(null);
    }

    protected PageRequest wrapPageable(Sort.Direction direction, String... fields) {
        return wrapPageable(Sort.by(direction, fields));
    }

    /**
     * 组装分页条件
     */
    protected <T> PageRequest wrapPageable(Sort sort) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 分页查询的每页查询条数
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        // 当前分页
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

        return PageHelper.wrapPageableForSpring(pageNo, pageSize, sort);
    }

    /**
     * 包装分页对象
     *
     * @param pn 页码
     * @param pn 页码
     * @return
     */
    protected PageRequest wrapPageable(Integer pn, Integer pageSize) {
        return PageHelper.wrapPageableForSpring(pn, pageSize);
    }

    protected String view(String view) {
        return "/" + siteOptions.getValue("theme") + view;
    }

    /**
     *
     * @param username 用户名
     * @param password 密码明文
     * @param rememberMe
     * @return
     */
    protected Result<AccountProfile> executeLogin(String username, String password, boolean rememberMe) {
        Result<AccountProfile> ret = Result.failure("登录失败");

        String pw = userManagerService.tryLogin(username, password);
        if (StringUtils.isEmpty(pw)) {
            return ret;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, pw, rememberMe);

        try {
            SecurityUtils.getSubject().login(token);
            ret = Result.success(getProfile());
        } catch (UnknownAccountException e) {
            logger.error(e.getMessage());
            ret = Result.failure("用户不存在");
        } catch (LockedAccountException e) {
            logger.error(e.getMessage());
            ret = Result.failure("用户被禁用");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            ret = Result.failure("用户认证失败");
        }
        return ret;
    }
}
