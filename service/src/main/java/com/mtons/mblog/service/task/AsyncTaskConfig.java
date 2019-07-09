package com.mtons.mblog.service.task;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.rapid.lang.thread.executor.MonitorThreadPoolExecutor;
import com.yueny.rapid.lang.thread.factory.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 11:18
 */
@Configuration
@EnableAsync
public class AsyncTaskConfig {
    @Bean
    public ListeningExecutorService taskJobExecutor() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("task-pool");

        ExecutorService es = new MonitorThreadPoolExecutor(2, 8,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        ListeningExecutorService executor = MoreExecutors.listeningDecorator(es);

        return executor;
    }

}
