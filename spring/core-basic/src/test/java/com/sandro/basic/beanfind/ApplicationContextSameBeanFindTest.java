package com.sandro.basic.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sandro.basic.member.MemberRepository;
import com.sandro.basic.member.MemoryMemberRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @DisplayName("타입으로 조회시 같은 타입이 여러 개라면 예외가 발생한다.")
    @Test
    void findBeanByTypeDuplicate() throws Exception {
        assertThatThrownBy(() -> ac.getBean(MemberRepository.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }

    @DisplayName("같은 타입이 여러 개라면 빈 이름을 지정한다.")
    @Test
    void findBeanByName() throws Exception {
        assertThatCode(() -> {
            ac.getBean("memberRepository1", MemberRepository.class);
            ac.getBean("memberRepository2", MemberRepository.class);
        }).doesNotThrowAnyException();
    }

    @DisplayName("특정 타입을 모두 조회하기")
    @Test
    void findAllBeansByType() throws Exception {
        Map<String, MemberRepository> beans = ac.getBeansOfType(MemberRepository.class);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + "Object = " + beans.get(key));
        }
        assertThat(beans).hasSize(2);
    }

    @Configuration
    static class SameBeanConfig {
        @Bean public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
