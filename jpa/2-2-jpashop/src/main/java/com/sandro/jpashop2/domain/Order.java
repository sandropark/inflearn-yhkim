package com.sandro.jpashop2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sandro.jpashop2.domain.DeliveryStatus.COMP;
import static com.sandro.jpashop2.domain.OrderStatus.CANCEL;
import static com.sandro.jpashop2.domain.OrderStatus.ORDER;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();     // 양방향 매핑

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(STRING)
    private OrderStatus status;

    // == 연관관계 메서드 == //

    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();                  // 1. order 생성
        order.setMember(member);                    // 2. 맴버 설정
        order.setDelivery(delivery);                // 3. 배송 설정
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);          // 4. 주문 상품 설정
        }
        order.setStatus(ORDER);                     // 5. 주문 상태 설정
        order.setOrderDate(LocalDateTime.now());    // 6. 주문 날짜 설정
        return order;
    }

    // == 비즈니스 로직 == //
    // 주문 취소
    public void cancel() {
        if (delivery.getStatus() == COMP) {     // 배송이 완료됐다면 취소 불가.
            throw new IllegalStateException("배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(CANCEL);                     // 상태 변경
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();                     // 재고 수량 복구
        }
    }

    // == 조회 로직 == //
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    private void setOrderDate(LocalDateTime localDateTime) {
        this.orderDate = localDateTime;
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    private void setMember(Member member) {
        this.member = member;
    }
}
