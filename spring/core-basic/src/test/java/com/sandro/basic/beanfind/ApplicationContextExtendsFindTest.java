package com.sandro.basic.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sandro.basic.discount.DiscountPolicy;
import com.sandro.basic.discount.FixDiscountPolicy;
import com.sandro.basic.discount.RateDiscountPolicy;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @DisplayName("부모타입으로 조회시, 자식이 둘 이상 있으면 예외가 발생한다.")
    @Test
    void findBeanBySuperTypeDuplicate() throws Exception {
        assertThatThrownBy(() -> ac.getBean(DiscountPolicy.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }

    @DisplayName("부모타입으로 조회시, 자식이 둘 이상 있으면 이름을 지정하면 된다.")
    @Test
    void findBeanByNameAndSuperType() throws Exception {
        assertThatCode(() -> {
            ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
            ac.getBean("fixDiscountPolicy", DiscountPolicy.class);
        }).doesNotThrowAnyException();
    }

    @DisplayName("부모타입으로 모두 조회한다.")
    @Test
    void findAllBeansBySuperType() throws Exception {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType).hasSize(2);
    }

    @Configuration
    static class TestConfig {
        @Bean public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
        @Bean public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }

}
