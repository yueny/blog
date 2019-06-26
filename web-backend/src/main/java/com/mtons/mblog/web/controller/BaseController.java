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
import com.mtons.mblog.base.storage.StorageFactory;
import com.mtons.mblog.config.SiteOptions;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.service.manager.IUserManagerService;
import com.mtons.mblog.web.formatter.StringEscapeEditor;
import com.yueny.rapid.lang.agent.UserAgentResource;
import com.yueny.rapid.lang.agent.handler.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controller 基类
 *
 * @author langhsu
 * @since 3.0
 */
public class BaseController {
    @Autowired
    protected StorageFactory storageFactory;
    @Autowired
    protected SiteOptions siteOptions;
    @Autowired
    private IUserManagerService userManagerService;

    /**
     * 存放当前线程的HttpServletRequest对象
     */
    private final static ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();
    /**
     * 存放当前线程的Model对象
     */
    private final static ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();
    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

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

    /**
     * 组装分页条件
     */
    protected <T> PageRequest wrapPageable(Sort sort) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

        if (null == sort) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    /**
     * 包装分页对象
     *
     * @param pn 页码
     * @param pn 页码
     * @return
     */
    protected PageRequest wrapPageable(Integer pn, Integer pageSize) {
        if (pn == null || pn == 0) {
            pn = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        return PageRequest.of(pn - 1, pageSize);
    }

    /**
     * 获取当前线程的UserAgent
     */
    protected UserAgentResource getCurrentUserAgent() {
        return UserAgentUtils.getCurrentUserAgent(getRequest());
    }

    /**
     * 获取当前线程的Model对象
     *
     * @return 当前线程的Model对象
     */
    protected Model getModel() {
        return modelThreadLocal.get();
    }

    /**
     * 获取当前线程的HttpServletRequest对象
     *
     * @return 当前线程的HttpServletRequest对象
     */
    protected HttpServletRequest getRequest() {
        return httpServletRequestThreadLocal.get();
    }

    /**
     * 使用@ModelAttribute注解标识的方法会在每个控制器中的方法访问之前先调用
     *
     * @param request
     * @param model
     */
    @ModelAttribute
    protected void setThreadLocal(final HttpServletRequest request, final Model model) {
        httpServletRequestThreadLocal.set(request);
        modelThreadLocal.set(model);
    }

    /**
     * 从 HttpServletRequest 中获取属性值
     *
     * @param name
     *            属性名
     * @return 属性值
     */
    protected Object getRequestAttribute(final String name) {
        final HttpServletRequest request = this.getRequest();
        final Object value = request.getAttribute(name);
        return value;
    }

    /**
     * 向 Model 设置属性值
     *
     * @param name
     *            属性名
     * @param value
     *            属性值
     */
    protected void setModelAttribute(final String name, final Object value) {
        getModel().addAttribute(name, value);
    }

    /**
     * 向 HttpServletRequest 设置属性值
     *
     * @param name
     *            属性名
     * @param value
     *            属性值
     */
    protected void setRequestAttribute(final String name, final Object value) {
        final HttpServletRequest request = this.getRequest();
        request.setAttribute(name, value);
    }

    /**
     * 请求重定向
     *
     * @param url
     *            重定向请求
     * @return 重定向请求
     */
    protected String redirectAction(final String url) {
        return String.format("redirect:%s", url);
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
