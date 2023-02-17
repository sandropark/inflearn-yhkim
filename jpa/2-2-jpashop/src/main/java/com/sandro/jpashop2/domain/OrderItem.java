package com.sandro.jpashop2.domain;

import com.sandro.jpashop2.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int orderPrice;
    private int count;

    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();      // 1. 객체 생성
        orderItem.setItem(item);                    // 2. item 세팅
        orderItem.setOrderPrice(orderPrice);        // 3. 가격 세팅
        orderItem.setCount(count);                  // 4. count 세팅

        item.decreaseStock(count);                  // 5. 주문 들어온 수만큼 재고 줄이기
        return orderItem;
    }

    // == 비즈니스 로직 == //

    // 주문 취소
    public void cancel() {
        getItem().increaseStock(getCount());
    }

    // == 조회 로직 == //

    // 주문 가격 전체 조회 (가격 * 수량)
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private void setItem(Item item) {
        this.item = item;
    }

    private void setCount(int count) {
        this.count = count;
    }

    private void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
