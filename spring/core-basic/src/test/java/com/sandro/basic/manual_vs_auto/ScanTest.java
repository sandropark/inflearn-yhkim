package com.sandro.basic.manual_vs_auto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class ScanTest {

    @Test
    void manualTest() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        AutoAppConfig autoAppConfig = ac.getBean(AutoAppConfig.class);
        OrderService orderService = ac.getBean(OrderService.class);
        OrderRepository orderRepository1 = orderService.getOrderRepository();
        OrderRepository orderRepository2 = ac.getBean(OrderRepository.class);
        System.out.println("autoAppConfig = " + autoAppConfig);
        System.out.println("orderService = " + orderService);
        System.out.println("orderRepository1 = " + orderRepository1);
        System.out.println("orderRepository2 = " + orderRepository2);
    }

    @Test
    void autoTest() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ManualAppConfig.class);
        ManualAppConfig manualAppConfig = ac.getBean(ManualAppConfig.class);
        OrderService orderService = ac.getBean(OrderService.class);
        OrderRepository orderRepository1 = orderService.getOrderRepository();
        OrderRepository orderRepository2 = ac.getBean(OrderRepository.class);
        System.out.println("manualAppConfig = " + manualAppConfig);
        System.out.println("orderService = " + orderService);
        System.out.println("orderRepository1 = " + orderRepository1);
        System.out.println("orderRepository2 = " + orderRepository2);
    }

//    @Configuration
    @ComponentScan(
            excludeFilters = @Filter(Configuration.class)
    )
    static class AutoAppConfig {
    }

    @Configuration
    static class ManualAppConfig {
        @Bean
        public OrderService orderService() {
            return new OrderService(orderRepository());
        }

        @Bean
        public OrderRepository orderRepository() {
            return new OrderRepository();
        }
    }

    @Component
    static class OrderService {
        private final OrderRepository orderRepository;

        @Autowired
        public OrderService(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        public OrderRepository getOrderRepository() {
            return orderRepository;
        }
    }

    @Component
    static class OrderRepository {}
}

