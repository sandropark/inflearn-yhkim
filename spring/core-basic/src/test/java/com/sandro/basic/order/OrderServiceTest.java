package com.sandro.basic.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sandro.basic.AppConfig;
import com.sandro.basic.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static com.sandro.basic.member.Grade.BASIC;
import static com.sandro.basic.member.Grade.VIP;

class OrderServiceTest {

    AppConfig appConfig = new AppConfig();
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() throws Exception {
        // Given
        Member memberA = new Member(1L, "memberA", VIP);
        Member memberB = new Member(2L, "memberB", BASIC);
        memberService.join(memberA);
        memberService.join(memberB);

        // When
        Order orderA = orderService.createOrder(memberA.getId(), "가위", 10000);
        Order orderB = orderService.createOrder(memberB.getId(), "밥솥", 15000);

        // Then
        assertThat(orderA.calculatePrice()).isEqualTo(9000);
        assertThat(orderB.calculatePrice()).isEqualTo(15000);
    }

}