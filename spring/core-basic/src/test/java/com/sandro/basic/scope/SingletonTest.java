package com.sandro.basic.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @DisplayName("logic()을 호출할 때마다 새로운 프로토타입 빈을 생성한다.")
    @Test
    void test() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        int count1 = singletonBean1.logic();
        assertThat(count1).isEqualTo(1);

        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        int count2 = singletonBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @RequiredArgsConstructor
    @Scope("singleton")
    static class SingletonBean {

        private final Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.count();
            return prototypeBean.count;
        }

    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count;

        public void count() {
            count++;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }
    }
}
