package com.sandro.proxy.config.v1_proxy;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.app.v2.OrderControllerV2;
import com.sandro.proxy.app.v2.OrderRepositoryV2;
import com.sandro.proxy.app.v2.OrderServiceV2;
import com.sandro.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import com.sandro.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import com.sandro.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ConcreteProxyConfig {

    private final LogTrace trace;

    @Bean
    public OrderControllerV2 orderController() {
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderService()), trace);
    }

    @Bean
    public OrderServiceV2 orderService() {
        return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepository()), trace);
    }

    @Bean
    public OrderRepositoryV2 orderRepository() {
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), trace);
    }

}
