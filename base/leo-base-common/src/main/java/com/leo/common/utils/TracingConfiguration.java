package com.leo.common.utils;


import brave.Tracing;
import brave.context.slf4j.MDCScopeDecorator;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.DelegatingTracingFilter;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TracingConfiguration
 * @Description Zipkin和日誌整合
 * @Author liulu_leo
 * @Date 2020/11/10
 * @Version 1.0
 */

@Configuration
@Import({TracingClientHttpRequestInterceptor.class, SpanCustomizingAsyncHandlerInterceptor.class})
public class TracingConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    public DelegatingTracingFilter getDelegatingTracingFilter() {
        return new DelegatingTracingFilter();
    }

    @Bean
    Sender sender() {
        return OkHttpSender.create("http://127.0.0.1:9999/api/v2/spans");
    }

    @Bean
    AsyncReporter<Span> spanReporter() {
        return AsyncReporter.create(sender());
    }

    @Bean
    Tracing tracing(@Value("${zipkin.service:brave-webmvc-example}") String serviceName) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
                .spanReporter(spanReporter()).currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder()
                        //添加日志相关的处理器
                        .addScopeDecorator(MDCScopeDecorator.create()).build()).build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }


    @Autowired
    TracingClientHttpRequestInterceptor clientInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
        interceptors.add(clientInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Autowired
    SpanCustomizingAsyncHandlerInterceptor serverInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInterceptor);
    }
}

