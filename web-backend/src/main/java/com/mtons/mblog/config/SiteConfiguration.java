package main.java.com.mtons.mblog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import main.java.com.mtons.mblog.modules.template.TemplateDirective;
import main.java.com.mtons.mblog.modules.template.method.TimeAgoMethod;
import com.mtons.mblog.shiro.tags.ShiroTags;
import com.yueny.rapid.lang.thread.executor.MonitorThreadPoolExecutor;
import com.yueny.rapid.lang.thread.factory.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : langhsu
 * @since 3.0
 */
@Configuration
@EnableAsync
public class SiteConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void setSharedVariable() {
        Map<String, TemplateDirective> map = applicationContext.getBeansOfType(TemplateDirective.class);
        map.forEach((k, v) -> configuration.setSharedVariable(v.getName(), v));
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("shiro", new ShiroTags());


        // 在工具类中， 上下文初始化
        ApplicationContextHolder.setApplicationContext(applicationContext);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("siteConfiguration.logThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean
    public ListeningExecutorService executorService() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("siteConfiguration-pool");

        ExecutorService es = new MonitorThreadPoolExecutor(2, 8,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(es);

        return executor;
    }

    @Bean
    @ConditionalOnClass({JSON.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

//    @Bean
//    public HttpMessageConverters httpMessageConverters(){
//        FastJsonHttpMessageConverter jsonHttpMessageConverter = fastJsonHttpMessageConverter();
//        return new HttpMessageConverters(jsonHttpMessageConverter);
//    }
}
