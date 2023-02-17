package com.sandro.basic.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sandro.basic.AppConfig;
import com.sandro.basic.member.MemberRepository;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.member.MemberServiceImpl;
import com.sandro.basic.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    @Test
    void pureContainer() throws Exception {
        AppConfig appConfig = new AppConfig();
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @DisplayName("스프링 DI 컨테이너")
    @Test
    void springContainer() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }
    
    @Test
    void configurationTest() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository3 = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println("appConfig = " + bean);
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("ac.getBean -> memberRepository = " + memberRepository3);

        assertThat(memberRepository1).isSameAs(memberRepository3);
        assertThat(memberRepository2).isSameAs(memberRepository3);
    }

}
