package com.sandro.proxy.config.v3_proxyfactory;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.app.v2.OrderControllerV2;
import com.sandro.proxy.app.v2.OrderRepositoryV2;
import com.sandro.proxy.app.v2.OrderServiceV2;
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
public class ProxyFactoryConfigV2 {

    private final LogTrace trace;
    private DefaultPointcutAdvisor advisor;
    private ProxyFactory factory;

    @Bean
    public OrderControllerV2 orderControllerV1(OrderServiceV2 orderService) {
        OrderControllerV2 target = new OrderControllerV2(orderService);
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV2 orderServiceV1(OrderRepositoryV2 orderRepository) {
        OrderServiceV2 target = new OrderServiceV2(orderRepository);
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), target.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2() {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        factory = new ProxyFactory(target);
        factory.addAdvisor(advisor);
        OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();
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
