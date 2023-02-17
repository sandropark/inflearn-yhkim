package com.sandro.basic.discount;

import com.sandro.basic.member.Member;

public interface DiscountPolicy {
    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
