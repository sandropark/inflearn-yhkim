package com.sandro.jpashop.service;

import com.sandro.jpashop.domain.item.Book;
import com.sandro.jpashop.domain.item.Item;
import com.sandro.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품저장() throws Exception {
        // given
        Item book = new Book();  // 상품 생성

        // when
        itemRepository.save(book);  // 상품 저장

        // then
        Item foundItem = itemRepository.findOne(book.getId());  // 상품 꺼내오기
        assertThat(book).isEqualTo(foundItem);                  // 두 상품이 같은지 비교
    }

}