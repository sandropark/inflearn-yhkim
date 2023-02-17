package com.sandro.basic.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.sandro.basic.member.Grade;
import com.sandro.basic.member.Member;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @DisplayName("VIP는 10% 할인된다.")
    @ParameterizedTest
    @CsvSource({"10000, 1000", "3500, 350"})
    void vip_o(int price, int expectedDiscountPrice) throws Exception {
        // Given
        Member vipMember = new Member(1L, "VIP", Grade.VIP);

        // When
        int discountPrice = discountPolicy.discount(vipMember, price);

        // Then
        assertThat(discountPrice).isEqualTo(expectedDiscountPrice);
    }

    @DisplayName("VIP가 아니면 할인이 적용되지 않는다.")
    @Test
    void vip_x() throws Exception {
        // Given
        Member basicMember = new Member(2L, "basic", Grade.BASIC);

        // When
        int discountPrice = discountPolicy.discount(basicMember, 10000);

        // Then
        assertThat(discountPrice).isEqualTo(0);
    }
    
}
