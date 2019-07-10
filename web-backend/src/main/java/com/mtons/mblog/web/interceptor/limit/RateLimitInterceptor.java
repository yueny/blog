package com.mtons.mblog.web.interceptor.limit;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring请求限流器
 * 可对全局请求或url表达式请求进行限流
 * 内部使用spring DispatcherServlet的匹配器PatternsRequestCondition
 * 进行url的匹配。
 *
 * 限流针对IP
 *
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-10 08:35
 */
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {
    private RateLimiter globalRateLimiter;

    /**
     * 限流配置项
     */
    private Map<String, Integer> urlProperties;

    private Map<PatternsRequestCondition, RateLimiter> urlRateMap;

    private UrlPathHelper urlPathHelper;

    private int globalRate;

    private List<URLLimitMapping> limitMappings;

    public RateLimitInterceptor() {
        this(0);
    }

    /**
     * @param globalRate 全局请求限制qps
     */
    public RateLimitInterceptor(int globalRate) {
        urlPathHelper = new UrlPathHelper();
        this.globalRate = globalRate;
        if (globalRate > 0)
            globalRateLimiter = RateLimiter.create(globalRate);
    }

    /**
     * @param globalRate 全局请求限制qps
     */
    public void setGlobalRate(int globalRate) {
        this.globalRate = globalRate;
        if (globalRate > 0)
            globalRateLimiter = RateLimiter.create(globalRate);
    }

    /**
     * url限流
     *
     * @param urlProperties url表达式->qps propertis
     */
    public void setUrlProperties(Map<String, Integer> urlProperties) {
        if (urlRateMap == null){
            urlRateMap = new ConcurrentHashMap<>();
        }

        fillRateMap(urlProperties, urlRateMap);
        this.urlProperties = urlProperties;
    }

    /**
     * url限流
     *
     * @param limitMappings url表达式 array->qps mapping
     */
    public void setLimitMappings(List<URLLimitMapping> limitMappings) {
        if (urlRateMap == null){
            urlRateMap = new ConcurrentHashMap<>();
        }

        fillRateMap(limitMappings, urlRateMap);
        this.limitMappings = limitMappings;
    }

    /**
     * 将url表达转换为PatternsRequestCondition，并生成对应RateLimiter
     * 保存
     *
     * @param urlProperties
     * @param map
     */
    private void fillRateMap(Map<String, Integer> urlProperties, Map<PatternsRequestCondition, RateLimiter> map) {
        if (urlProperties != null && !urlProperties.isEmpty()) {
            for (Map.Entry<String, Integer> entry : urlProperties.entrySet()) {
                Integer value = entry.getValue();
                map.put(new PatternsRequestCondition(entry.getKey()), RateLimiter.create(Double.valueOf(value)));
            }
        }
    }

    /**
     * 将url表达转换为PatternsRequestCondition，并生成对应RateLimiter
     * 保存
     *
     * @param limitMappings
     * @param map
     */
    private void fillRateMap(List<URLLimitMapping> limitMappings, Map<PatternsRequestCondition, RateLimiter> map) {
        if (limitMappings != null) {
            for (URLLimitMapping mapping : limitMappings) {
                map.put(new PatternsRequestCondition(mapping.getUrls()), RateLimiter.create(Double.valueOf(mapping.getRate())));
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (urlRateMap != null) {
            //当前请求路径
            String lookupPath = urlPathHelper.getLookupPathForRequest(request);
            //迭代所有url表达式对应的PatternsRequestCondition
            for (PatternsRequestCondition patternsRequestCondition : urlRateMap.keySet()) {
                //进行匹配, 使用spring DispatcherServlet的匹配器PatternsRequestCondition进行匹配
                List<String> matches = patternsRequestCondition.getMatchingPatterns(lookupPath);
                if (!matches.isEmpty()) {
                    //匹配成功的则获取对应限流器的令牌
                    if (urlRateMap.get(patternsRequestCondition).tryAcquire()) {
                        log.info(lookupPath + " 请求匹配到" + Joiner.on(",").join(patternsRequestCondition.getPatterns()) + "限流器");
                    } else {
                        //获取令牌失败
                        log.info(lookupPath + " 请求超过" + Joiner.on(",").join(patternsRequestCondition.getPatterns()) + "限流器速率");
                        return false;
                    }
                }
            }
        }

        //全局限流
        if (globalRateLimiter != null) {
            if (!globalRateLimiter.tryAcquire()) {
                return false;
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
