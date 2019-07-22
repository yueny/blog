/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.interceptor;

import com.mtons.mblog.base.consts.StorageConsts;
import com.mtons.mblog.model.SiterProfile;
import com.mtons.mblog.modules.comp.ISiteOptionsGetService;
import com.mtons.mblog.model.SiteOptionsControlsVO;
import com.mtons.mblog.modules.hook.interceptor.InterceptorHookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 基础拦截器 - 向 request 中添加一些基础变量.
 *
 * 需要注入Spring管理的对象，所以加入spring contexts
 * 
 * @author langhsu
 * 
 */
@Component
public class BaseContextInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private InterceptorHookManager interceptorHookManager;
	@Autowired
	private ISiteOptionsGetService siteOptionsGetService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		interceptorHookManager.preHandle(request, response, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("base", request.getContextPath());

		// 获取站点相关的配置， 不加载数据库配置，故只包含配置中心的配置
		SiteOptionsControlsVO controlsVo = siteOptionsGetService.getControls();

		// site 配置
		SiterProfile siterProfile = SiterProfile.builder()
				.commentAllowAnonymous(controlsVo.isCommentAllowAnonymous())
				.version(siteOptionsGetService.getVersion())
				// 用户默认头像路径或地址
				.userDefaultAvatar(StorageConsts.AVATAR)
				.build();
		request.setAttribute("siterProfile", siterProfile);

		interceptorHookManager.postHandle(request,response,handler,modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		interceptorHookManager.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
		interceptorHookManager.afterConcurrentHandlingStarted(request, response, handler);
	}

}
