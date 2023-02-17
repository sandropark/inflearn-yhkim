package com.sandro.itemservice.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    Item item;

    @BeforeEach
    void setUp() {
        item = new Item("충전기", 3000, 10);
    }

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void saveAndFind() throws Exception {
        // given
        Item savedItem = itemRepository.save(item);

        // when
        Item foundItem = itemRepository.findById(savedItem.getId());

        // then
        assertThat(foundItem).isSameAs(savedItem);
    }

    @Test
    void findAll() throws Exception {
        // given
        Item savedItem = itemRepository.save(item);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items).contains(savedItem);
    }

    @Test
    void update() throws Exception {
        // given
        Item savedItem = itemRepository.save(item);
        String beforeName = item.getItemName();

        // when
        itemRepository.update(savedItem.getId(), new Item("바나나", 2000, 5));
        Item modifiedItem = itemRepository.findById(savedItem.getId());

        // then
        assertThat(beforeName).isEqualTo("충전기");
        assertThat(modifiedItem.getItemName()).isEqualTo("바나나");

    }

}