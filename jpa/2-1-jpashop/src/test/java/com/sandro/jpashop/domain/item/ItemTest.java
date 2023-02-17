package com.sandro.jpashop.domain.item;

import com.sandro.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    Book book;

    @BeforeEach
    public void setUp() throws Exception {
        book = new Book();
        book.setStockQuantity(10);
    }

    @Test
    public void decreaseStock_재고부족예외발생() {
        // given

        // when
        assertThatThrownBy(() -> book.decreaseStock(11))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    public void decreaseStock_정상통과() throws Exception {
        // given

        // when
        book.decreaseStock(10);

        // then
        assertEquals(0, book.getStockQuantity());
    }

}