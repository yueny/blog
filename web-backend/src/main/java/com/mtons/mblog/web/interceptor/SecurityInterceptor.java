package com.mtons.mblog.web.interceptor;

import com.mtons.mblog.service.atom.jpa.AttackIpService;
import com.mtons.mblog.service.comp.configure.impl.ConfigureGetService;
import com.mtons.mblog.service.util.LogUtil;
import com.mtons.mblog.service.util.formatter.JsonUtils;
import com.yueny.rapid.data.resp.pojo.response.BaseResponse;
import com.yueny.rapid.lang.util.IpUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 请求地址的安全拦截器
 */
@Component
@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private AttackIpService attackIpService;
	// 单例，所以只实例化一次
	private NamedThreadLocal<StopWatch> stopWatchThreadLocal = new NamedThreadLocal<StopWatch>("Security-Local");

	/**
	 * 是否开启性能损耗监控。 默认开启
	 */
	@Setter
	private boolean isWatch= true;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(isWatch){
			// DurationTimer durationTimer = new DurationTimer();
			StopWatch watch = new StopWatch();
			watch.start("白名单控制");

			stopWatchThreadLocal.set(watch);
		}

		String clientIp = IpUtil.getClientIp(request);

		//得到配置中心白名单配置
		Set<String> ips =  ConfigureGetService.getSecurityIpWhite();

		// 白名单， 放行
		if(ips.contains(clientIp)){
			stopWatchThreadLocal.remove();
			return true;
		}

		if(isWatch){
			StopWatch watch = stopWatchThreadLocal.get();
			watch.stop();

			watch.start("黑名单控制");
		}

		boolean isAllow = false;
		// 黑名单判断， 在黑名单中则拒绝
		Set<String> attackIps = attackIpService.findAllIps();
		if(attackIps.contains(clientIp)){
			BaseResponse result = new BaseResponse();
			result.setCode("403");
			result.setMessage("访问源存在安全风险，拒绝提供服务!");

			response.setStatus(403);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(JsonUtils.toJson(result));

			log.debug("【安全拦截】黑名单访问:{} , 拒绝！", clientIp);
		}else{
			isAllow = true;
		}

		if(isWatch){
			StopWatch watch = stopWatchThreadLocal.get();
			watch.stop();
			LogUtil.profileLogDebug("【安全拦截】安全拦截地址: [{}]访问， 拦截器耗时:{}", request.getRequestURI(), watch.prettyPrint());
		}

		return isAllow;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);

		if(isWatch){
			stopWatchThreadLocal.remove();
		}
	}

}
