package com.sandro.jpashop2.domain;

import com.sandro.jpashop2.domain.item.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @DisplayName("주문 상품 생성 : 상품의 재고가 주문수량만큼 줄어야 한다.")
    @Test
    void createOrderItem() throws Exception {
        // given
        int stockQuantity = 10;
        int price = 20000;
        Book book = new Book("사피엔스", price, stockQuantity, "하라리", "12345678");

        // when
        int orderQuantity = 5;
        OrderItem orderItem = OrderItem.createOrderItem(book, price, orderQuantity);

        // then
        assertThat(orderItem.getItem().getStockQuantity())
                .isEqualTo(stockQuantity - orderQuantity);
    }
}