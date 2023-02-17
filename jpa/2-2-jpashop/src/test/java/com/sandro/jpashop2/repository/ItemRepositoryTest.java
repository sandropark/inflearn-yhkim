package com.sandro.jpashop2.repository;

import com.sandro.jpashop2.domain.item.Book;
import com.sandro.jpashop2.domain.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    Book book;

    @BeforeEach
    void setUp() {
        book = new Book("사피엔스", 10000, 10, "하라리", "12345678");
    }

    @DisplayName("아이템 등록")
    @Test
    void save() throws Exception {
        // given

        // when
        itemRepository.save(book);
        Item foundItem = itemRepository.findOne(book.getId());

        // then
        assertThat(foundItem).isSameAs(book);
    }

    @DisplayName("아이템 전체 조회")
    @Test
    void findAll() throws Exception {
        // given
        itemRepository.save(book);

        // when
        List<Item> foundItems = itemRepository.findAll();

        // then
        assertThat(foundItems).contains(book);
        assertThat(foundItems).hasSize(1);
    }
}