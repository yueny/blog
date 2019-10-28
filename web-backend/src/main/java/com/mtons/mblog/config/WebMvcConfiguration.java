package com.mtons.mblog.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mtons.mblog.service.config.SiteOptions;
import com.mtons.mblog.web.interceptor.BaseContextInterceptor;
import com.mtons.mblog.web.interceptor.SecurityInterceptor;
import com.mtons.mblog.web.interceptor.StopWatchHandlerInterceptor;
import com.mtons.mblog.web.interceptor.ViewInterceptor;
import com.mtons.mblog.web.interceptor.limit.RateLimitInterceptor;
import com.yueny.rapid.data.log.trace.web.filter.mdc.WebLogMdcHandlerInterceptor;
import com.yueny.rapid.lang.agent.UserAgentHandlerMethodArgumentResolver;
import com.yueny.rapid.lang.agent.handler.UserAgentResolverHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langhsu
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private BaseContextInterceptor baseContextInterceptor;
    @Autowired
    private SecurityInterceptor securityInterceptor;
    @Autowired
    private ViewInterceptor viewInterceptor;

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    @Autowired
    private SiteOptions siteOptions;

    /**
     * 全局的静态资源和文件地址
     */
    private static final List<String> RESOURCE_PATH = new ArrayList<>();
    static{
        RESOURCE_PATH.add("/dist/**");
        RESOURCE_PATH.add("/store/**");
        RESOURCE_PATH.add("/static/**");
        // 不包含 static下的 examples
        // 不包含 本地存储文件 storage
    }

    /**
     * Add intercepter
     *
     * LogMdc --> 上下文 context --> 安全拦截 --> 限流 --> 性能 --> userAgent --> 浏览view --> ok
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LogMdc
        WebLogMdcHandlerInterceptor interceptor= new WebLogMdcHandlerInterceptor();
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**");

        // 上下文 context base
        registry.addInterceptor(baseContextInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(RESOURCE_PATH);

        // 安全拦截
        securityInterceptor.setWatch(false);
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(RESOURCE_PATH);

        // 限流
        registry.addInterceptor(rateLimitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(RESOURCE_PATH)
                .excludePathPatterns(
                        "/theme/**",
                        "/storage/**");

        // 性能
        registry.addInterceptor(stopWatchHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(RESOURCE_PATH)
                .excludePathPatterns(
                        "/theme/**",
                        "/storage/**");

        // userAgent
        registry.addInterceptor(userAgentResolverHandlerInterceptor())
                .addPathPatterns("/**");

        // 浏览view
        registry.addInterceptor(viewInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(RESOURCE_PATH)
                .excludePathPatterns(
                        "/theme/**",
                        "/storage/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) 	{
        argumentResolvers.add(userAgentHandlerMethodArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:///" + siteOptions.getLocation();
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/static/dist/");

        registry.addResourceHandler("/theme/*/dist/**")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations(location + "/storage/templates/");

        registry.addResourceHandler("/storage/avatars/**")
                .addResourceLocations(location + "/storage/avatars/");

        registry.addResourceHandler("/storage/thumbnails/**")
                .addResourceLocations(location + "/storage/thumbnails/");

        registry.addResourceHandler("/storage/blognails/**")
                .addResourceLocations(location + "/storage/blognails/");

        registry.addResourceHandler("/storage/vague/**")
                .addResourceLocations(location + "/storage/vague/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * UserAgent Interceptor
     */
    @Bean
    public UserAgentResolverHandlerInterceptor userAgentResolverHandlerInterceptor() {
        return new UserAgentResolverHandlerInterceptor();
    }

    /**
     * UserAgent
     */
    @Bean
    public UserAgentHandlerMethodArgumentResolver userAgentHandlerMethodArgumentResolver() 	{
        return new UserAgentHandlerMethodArgumentResolver();
    }

    /**
     * 性能 Interceptor
     */
    @Bean
    public StopWatchHandlerInterceptor stopWatchHandlerInterceptor() {
        return new StopWatchHandlerInterceptor();
    }

    /**
     * 限流 Interceptor
     */
    @Bean
    public RateLimitInterceptor rateLimitInterceptor() 	{
        RateLimitInterceptor rateLimitInterceptor = new RateLimitInterceptor();

        //限流配置项
        Map<String, Integer> urlProperties = new HashMap<>();
//        urlProperties.put("/", 100);

        rateLimitInterceptor.setUrlProperties(urlProperties);
        return rateLimitInterceptor;
    }

}
