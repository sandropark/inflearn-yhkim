package com.sandro.proxy.config.v3_proxyfactory;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.app.v1.*;
import com.sandro.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProxyFactoryConfigV1 {

    private final LogTrace trace;
    private DefaultPointcutAdvisor advisor;
    private ProxyFactory factory;

    @Bean
    public OrderControllerV1 orderControllerV1(OrderServiceV1 orderServiceV1) {
        OrderControllerV1Impl target = new OrderControllerV1Impl(orderServiceV1);
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderControllerV1 proxy = (OrderControllerV1) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(OrderRepositoryV1 orderRepositoryV1) {
        OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepositoryV1);
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderServiceV1 proxy = (OrderServiceV1) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderRepositoryV1 proxy = (OrderRepositoryV1) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @PostConstruct
    private void initAdvisor() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        advisor = new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }
}
