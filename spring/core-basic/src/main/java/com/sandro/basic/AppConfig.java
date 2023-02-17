package com.sandro.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sandro.basic.discount.DiscountPolicy;
import com.sandro.basic.discount.FixDiscountPolicy;
import com.sandro.basic.discount.RateDiscountPolicy;
import com.sandro.basic.member.MemberService;
import com.sandro.basic.member.MemberServiceImpl;
import com.sandro.basic.member.MemoryMemberRepository;
import com.sandro.basic.order.OrderService;
import com.sandro.basic.order.OrderServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
//        return new FixDiscountPolicy();
    }

}
