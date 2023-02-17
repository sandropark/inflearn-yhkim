package com.sandro.basic.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sandro.basic.AppConfig;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationContextBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @DisplayName("빈 이름으로 조회")
    @Test
    void findBeanByName() throws Exception {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("구체 타입으로 조회")
    @Test
    void findBeanByName2() throws Exception {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("이름 없이 타입으로만 조회")
    @Test
    void findBeanByType() throws Exception {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("빈 이름이 없는 경우 예외가 발생한다.")
    @Test
    void findBeanByNameX() throws Exception {
        assertThatThrownBy(() -> ac.getBean("xxxx", MemberService.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

}
