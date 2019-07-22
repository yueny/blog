package com.mtons.mblog.web.interceptor;

import com.mtons.mblog.service.util.LogUtil;
import lombok.Setter;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 性能拦截器
 */
public class StopWatchHandlerInterceptor extends HandlerInterceptorAdapter {
	// 单例，所以只实例化一次
	private NamedThreadLocal<StopWatch> stopWatchThreadLocal = new NamedThreadLocal<StopWatch>("StopWatch-StartTime");

	/**
	 * 默认为超过 1500 ms即为慢操作
	 */
	@Setter
	// 优先读取配置中心配置项：${digest.print.slow.dump.threshold} 的实时变更
	private Long slowDumpThreshold = 1500L;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		StopWatch stopWatch = new StopWatch(request.getRequestURI() + "$" + System.currentTimeMillis());
		stopWatch.start();

		// 线程绑定变量（该数据只有当前请求的线程可见）
		stopWatchThreadLocal.set(stopWatch);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 得到线程绑定的局部变量（开始时间
		StopWatch stopWatch = stopWatchThreadLocal.get();

		super.afterCompletion(request, response, handler, ex);

		stopWatch.stop();
		//此处认为处理时间超过 1500 毫秒的请求为慢请求
		if(stopWatch.getTotalTimeMillis() > slowDumpThreshold) {
			// 记录到日志文件
			LogUtil.slowlyProfileLogRecord("【性能拦截】服务地址: [{}] duration is slower than {}/{} ms.",
					request.getRequestURI(), stopWatch.getTotalTimeMillis(), slowDumpThreshold);
		}else{
			LogUtil.profileLog("【性能拦截】服务地址: [{}] 服务正常, 耗时 {} ms.",
					request.getRequestURI(), stopWatch.getTotalTimeMillis());
		}

		stopWatchThreadLocal.remove();
	}

}
