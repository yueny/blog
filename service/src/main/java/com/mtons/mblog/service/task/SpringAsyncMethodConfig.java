package com.mtons.mblog.service.task;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.rapid.lang.thread.executor.MonitorThreadPoolExecutor;
import com.yueny.rapid.lang.thread.factory.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 异步方法使用的线程信息定义
 *
 * 使用时在方法上加  @Async(value = "methodExecutor")  即可。
 * 使用@Async时要求是不能有返回值的。
 * 调用被@Async标记的方法的调用者不能和被调用的方法在同一类中不然不会起作用
 * 
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 11:18
 */
@Configuration
@EnableAsync
public class SpringAsyncMethodConfig {
    @Bean
    public Executor methodExecutor() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("biz-method-pool");

        ExecutorService es = new MonitorThreadPoolExecutor(2, 8,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        ListeningExecutorService executor = MoreExecutors.listeningDecorator(es);

        return executor;
    }

}
