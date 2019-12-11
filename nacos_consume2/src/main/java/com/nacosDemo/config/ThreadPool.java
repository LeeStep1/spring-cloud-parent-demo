package com.nacosDemo.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @description: 线程池
 * @author: liyang
 * @date: 2019-10-23
 **/
@SpringBootConfiguration
@EnableAsync
public class ThreadPool implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

        //核心线程池大小
        threadPoolExecutor.setCorePoolSize(3);

        //最大线程池大小
        threadPoolExecutor.setMaxPoolSize(5);

        //队列容量
        threadPoolExecutor.setQueueCapacity(15);

        threadPoolExecutor.initialize();

        return threadPoolExecutor;
    }
}
