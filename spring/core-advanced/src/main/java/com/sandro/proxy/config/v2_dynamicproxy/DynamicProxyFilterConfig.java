package com.sandro.proxy.config.v2_dynamicproxy;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.app.v1.*;
import com.sandro.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Proxy;

@RequiredArgsConstructor
@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = new String[]{"request*", "order*", "save*"};
    private final LogTrace trace;
    private LogTraceFilterHandler.LogTraceFilterHandlerFactory logTraceFilterHandlerFactory;

    @Bean
    public OrderControllerV1 orderControllerV1() {
        OrderControllerV1Impl target = new OrderControllerV1Impl(orderServiceV1());
        return (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                logTraceFilterHandlerFactory.getInstance(target)
        );
    }

    @Bean
    public OrderServiceV1 orderServiceV1() {
        OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepositoryV1());
        return (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                logTraceFilterHandlerFactory.getInstance(target)
        );
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
        return (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                logTraceFilterHandlerFactory.getInstance(target)
        );
    }

    @PostConstruct
    private void initHandlerFactory() {
        logTraceFilterHandlerFactory = new LogTraceFilterHandler.LogTraceFilterHandlerFactory(trace, PATTERNS);
    }

}
