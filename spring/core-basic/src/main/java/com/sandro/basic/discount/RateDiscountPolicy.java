package com.sandro.basic.discount;

import org.springframework.stereotype.Component;
import com.sandro.basic.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    private final int discountRate = 10;

    @Override
    public int discount(Member member, int price) {
        return member.isVip()
                ? price * discountRate / 100
                : 0;
    }

}
