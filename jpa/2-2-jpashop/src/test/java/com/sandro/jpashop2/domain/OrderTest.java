package com.sandro.jpashop2.domain;

import com.sandro.jpashop2.domain.item.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    Member member;
    Delivery delivery;
    OrderItem orderItem;
    int stockQuantity;
    int itemPrice;
    int orderQuantity;

    @BeforeEach
    void setUp() {
        Address address = new Address("seoul", "123st", "12345");
        member = new Member("sandro", address);
        stockQuantity = 10;
        itemPrice = 20000;
        Book book = new Book("사피엔스", itemPrice, stockQuantity, "하라리", "12345678");
        delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
        orderQuantity = 5;
        orderItem = OrderItem.createOrderItem(book, book.getPrice(), orderQuantity);
    }

    @DisplayName("주문 생성")
    @Test
    void createOrder() throws Exception {
        // given
        // setUp()

        // when
        Order order = Order.createOrder(member, delivery, orderItem);

        // then
        assertThat(order.getStatus()).isSameAs(OrderStatus.ORDER);              // 주문 상태
        assertThat(order.getOrderItems().get(0).getItem().getStockQuantity())
                .isEqualTo(stockQuantity - orderQuantity);              // 주문 수량 만큼 재고 감소
    }

    @DisplayName("총 주문 금액")
    @Test
    void getTotalPrice() throws Exception {
        // given
        Order order = Order.createOrder(member, delivery, orderItem);

        // when
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(itemPrice * orderQuantity);
    }

    @DisplayName("주문 취소 : 주문 상태 CANCEL, 재고 수량 원복")
    @Test
    void cancel() throws Exception {
        // given
        Order order = Order.createOrder(member, delivery, orderItem);

        // when
        order.cancel();

        // then
        assertThat(order.getStatus()).isSameAs(OrderStatus.CANCEL);
        assertThat(order.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(stockQuantity);
    }

    @DisplayName("주문 취소 예외 : 배송완료시 취소 불가 예외 발생")
    @Test
    void cancelException() throws Exception {
        // given
        Order order = Order.createOrder(member, delivery, orderItem);
        order.getDelivery().complete();

        // when & then
        assertThatThrownBy(order::cancel)
                .isInstanceOf(IllegalStateException.class);
    }

}