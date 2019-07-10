package com.mtons.mblog.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mtons.mblog.web.interceptor.BaseInterceptor;
import com.mtons.mblog.web.interceptor.SecurityInterceptor;
import com.mtons.mblog.web.interceptor.ViewInterceptor;
import com.mtons.mblog.web.interceptor.limit.RateLimitInterceptor;
import com.yueny.rapid.lang.agent.UserAgentHandlerMethodArgumentResolver;
import com.yueny.rapid.lang.agent.handler.UserAgentResolverHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langhsu
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private BaseInterceptor baseInterceptor;
    @Autowired
    private ViewInterceptor viewInterceptor;
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    @Autowired
    private SiteOptions siteOptions;

    /**
     * Add intercepter
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // context base
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**", "/store/**", "/static/**");

        // 安全
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**");

        // userAgent
        registry.addInterceptor(userAgentResolverHandlerInterceptor())
                .addPathPatterns("/**");

        // view
        registry.addInterceptor(viewInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**",
                        "/store/**",
                        "/static/**",
                        "/theme/**",
                        "/storage/**");

        // 限流
        registry.addInterceptor(rateLimitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**",
                        "/store/**",
                        "/static/**",
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

    @Bean
    public UserAgentResolverHandlerInterceptor userAgentResolverHandlerInterceptor() {
        return new UserAgentResolverHandlerInterceptor();
    }

    @Bean
    public UserAgentHandlerMethodArgumentResolver userAgentHandlerMethodArgumentResolver() 	{
        return new UserAgentHandlerMethodArgumentResolver();
    }

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
