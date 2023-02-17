package com.sandro.jpashop.domain;

import com.sandro.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
// 객체 생성은 메서드 호출로만 가능하게 하기 위해서 기본 생성자를 막아둔다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count;  // 수량

    //==생성 메서드==//

    /**
     * OrderItem이 생성되는 시점에 필요한 객체를 한 번에 받기위해서 사용한다.
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        /**
         * item객체에 가격 정보가 있지만 할인을 할 수도 있기 때문에 가격은 사용하는 것이 좋다.
         */
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 주문이 들어오면 해당 수량 만큼 재고를 줄여야 한다.
        item.decreaseStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        /**
         * 주문이 취소됐으니 해당 아이템의 재고를 증가
         */
        getItem().increaseStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
