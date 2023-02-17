package com.sandro.basic.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        statefulService1.order("수건", 10000);
        statefulService2.order("비누", 5000);

        System.out.println(statefulService1.getPrice());
        System.out.println(statefulService2.getPrice());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}

class StatefulService {
    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}