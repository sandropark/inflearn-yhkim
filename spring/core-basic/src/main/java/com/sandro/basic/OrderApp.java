package com.sandro.basic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sandro.basic.member.Grade;
import com.sandro.basic.member.Member;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.order.Order;
import com.sandro.basic.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "수건", 10000);
        int calculatePrice = order.calculatePrice();

        System.out.println("calculatePrice = " + calculatePrice);
    }
}
