package com.leo.common.utils;

import feign.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @ClassName SpringAsyncConfigurer
 * @Description
 * @Author liulu_leo
 * @Date 2020/11/11
 * @Version 1.0
 */
/*@Configuration
@EnableAsync*/
class SpringAsyncConfigurer extends AsyncConfigurerSupport {



}