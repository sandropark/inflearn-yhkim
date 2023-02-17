package com.sandro.basic.discount;

import com.sandro.basic.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private final int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        return member.isVip() ? discountFixAmount : 0;
    }

}
