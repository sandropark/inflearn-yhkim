package com.sandro.proxy.config.v1_proxy;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.app.v1.*;
import com.sandro.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import com.sandro.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import com.sandro.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class InterfaceProxyConfig {

    private final LogTrace trace;

    @Bean
    public OrderControllerV1 orderController() {
        return new OrderControllerInterfaceProxy(new OrderControllerV1Impl(orderService()), trace);
    }

    @Bean
    public OrderServiceV1 orderService() {
        return new OrderServiceInterfaceProxy(new OrderServiceV1Impl(orderRepository()), trace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository() {
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryV1Impl(), trace);
    }

}
