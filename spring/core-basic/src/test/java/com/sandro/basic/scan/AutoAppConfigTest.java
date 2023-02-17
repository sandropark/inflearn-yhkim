package com.sandro.basic.scan;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sandro.basic.AutoAppConfig;
import com.sandro.basic.member.MemberRepository;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.member.MemberServiceImpl;
import com.sandro.basic.order.OrderService;
import com.sandro.basic.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

class AutoAppConfigTest {

    @Test
    void basicScan() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);
        assertThat(orderService).isInstanceOf(OrderService.class);
    }

    @Test
    void configurationTest() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        AutoAppConfig bean = ac.getBean(AutoAppConfig.class);
        MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository3 = ac.getBean(MemberRepository.class);

        System.out.println("appConfig = " + bean);
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("ac.getBean -> memberRepository = " + memberRepository3);

        assertThat(memberRepository1).isSameAs(memberRepository3);
        assertThat(memberRepository2).isSameAs(memberRepository3);
    }
}
