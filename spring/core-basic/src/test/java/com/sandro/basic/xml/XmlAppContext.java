package com.sandro.basic.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.order.OrderService;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {

    @Test
    void xmlAppContext() throws Exception {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    void xmlAppContext2() throws Exception {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        OrderService orderService = ac.getBean("orderService", OrderService.class);
        assertThat(orderService).isInstanceOf(OrderService.class);
    }

}
