package com.sandro.jpashop2.repository.order;

import com.sandro.jpashop2.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
