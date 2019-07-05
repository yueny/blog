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

import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.modules.hook.interceptor.InterceptorHookManager;
import com.mtons.mblog.service.manager.IViewLogManagerService;
import com.yueny.rapid.lang.agent.UserAgentResource;
import com.yueny.rapid.lang.agent.handler.UserAgentUtils;
import com.yueny.rapid.lang.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览拦截器
 */
@Service
public class ViewInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private InterceptorHookManager interceptorHookManager;
	@Autowired
	private IViewLogManagerService viewLogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		interceptorHookManager.preHandle(request, response, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		try{
			UserAgentResource userAgent = (UserAgentResource)request.getAttribute(UserAgentUtils.CURRENT_USERAGENT_ATTRIBUTE);
			StringBuilder clentAgent = new StringBuilder();
			if(userAgent != null){
				clentAgent.append(userAgent.getBrowser().getName());
				clentAgent.append("&");
				clentAgent.append(userAgent.getBrowserType().getName());
				clentAgent.append("/");
				clentAgent.append(userAgent.getOperatingSystem().getName());
				clentAgent.append("/");
				clentAgent.append(userAgent.getDeviceType().getName());
			}

			ViewLogVO viewLogVO = ViewLogVO.builder()
					.clientIp(IpUtil.getClientIp(request))
					.clientAgent(clentAgent.toString())
					.resourcePath(request.getRequestURI())
					.build();
			viewLogService.record(viewLogVO);
		} catch(Exception e){
			e.printStackTrace();
		}

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
