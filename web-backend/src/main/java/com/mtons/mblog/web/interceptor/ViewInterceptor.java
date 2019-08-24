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

import com.mtons.mblog.bo.ViewLogBo;
import com.mtons.mblog.modules.hook.interceptor.InterceptorHookManager;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.yueny.rapid.lang.agent.UserAgentResource;
import com.yueny.rapid.lang.agent.handler.UserAgentUtils;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.mask.decorator.MaskfulDecorator;
import com.yueny.rapid.lang.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 浏览拦截器
 */
@Service
public class ViewInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private InterceptorHookManager interceptorHookManager;
	@Autowired
	private ViewLogService viewLogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try{
			UserAgentResource userAgent = (UserAgentResource)request.getAttribute(
					UserAgentUtils.CURRENT_USERAGENT_ATTRIBUTE);
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

			String method = request.getMethod();
			MaskfulDecorator maskfulDecorator = new MaskfulDecorator(getRequestParameter(request));
			String parameterJson = JsonUtil.toJson(maskfulDecorator.mask());
			ViewLogBo viewLogVO = ViewLogBo.builder()
					.clientIp(IpUtil.getClientIp(request))
					.clientAgent(clentAgent.toString())
					.method(method)
					.parameterJson(parameterJson)
					.resourcePath(request.getRequestURI())
//					.resourcePathDesc()
					.build();

			// TODO  此处应异步化实现
			viewLogService.insert(viewLogVO);
		} catch(Exception e){
			e.printStackTrace();
		}

		interceptorHookManager.preHandle(request, response, handler);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);

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

	/**
	 * 参数转换,key为空spring会自动过滤掉
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return 转换后的参数
	 */
	private Map<String, String> getRequestParameter(
			final HttpServletRequest request) {
		final Map<String, String> requestParam = new HashMap<String, String>();
		for (final Map.Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {
			final String key = entry.getKey();
			if (entry.getValue() == null
					|| StringUtils.isBlank(entry.getValue()[0])) {
				requestParam.put(key, StringUtils.EMPTY);
			}
			requestParam.put(key, entry.getValue()[0]);
		}
		return requestParam;
	}
}
