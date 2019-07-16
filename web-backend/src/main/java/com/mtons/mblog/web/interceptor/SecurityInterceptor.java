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

import com.mtons.mblog.service.atom.jpa.AttackIpService;
import com.mtons.mblog.service.comp.impl.ConfiguterGetService;
import com.yueny.rapid.lang.util.IpUtil;
import com.yueny.rapid.lang.util.time.DurationTimer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 安全拦截器
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private AttackIpService attackIpService;

	// logger name
	private static final Logger log = LoggerFactory.getLogger("SLOWLY-DIGEST-LOGGER");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		DurationTimer durationTimer = new DurationTimer();
		StopWatch watch = new StopWatch();

		watch.start("白名单控制");
		String clientIp = IpUtil.getClientIp(request);

		//得到配置中心白名单配置
		Set<String> ips =  ConfiguterGetService.getSecurityIpWhite();

		// 白名单， 放行
		if(ips.contains(clientIp)){
			watch = null;
			return true;
		}
		watch.stop();

		watch.start("黑名单控制");
		boolean isAllow = false;
		// 黑名单判断， 在黑名单中则拒绝
		Set<String> attackIps = attackIpService.findAllIps();
		if(attackIps.contains(clientIp)){
			log.debug("黑名单访问:{} , 拒绝！", clientIp);
		}else{
			isAllow = true;
		}
		watch.stop();
		log.debug("安全拦截 {} 访问， 拦截器耗时:{} ms。", request.getRequestURI(), watch.prettyPrint());

		return isAllow;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
