package com.sandro.jpashop2.domain.item;

import com.sandro.jpashop2.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {

    @DisplayName("재고 초과 주문시 예외가 발생해야 한다.")
    @Test
    void decreaseStock() throws Exception {
        // given
        int stockQuantity = 10;
        Book book = new Book("사피엔스", 10000, stockQuantity, "하라리", "12345678");

        // when & then
        assertThatThrownBy(() -> book.decreaseStock(stockQuantity + 1))
                .isInstanceOf(NotEnoughStockException.class);
        assertThatCode(() -> book.decreaseStock(stockQuantity))
                .doesNotThrowAnyException();
    }
}