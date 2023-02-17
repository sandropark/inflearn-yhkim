package com.sandro.jpashop2.service;

import com.sandro.jpashop2.controller.BookForm;
import com.sandro.jpashop2.domain.item.Book;
import com.sandro.jpashop2.domain.item.Item;
import com.sandro.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, BookForm form) {
        Book book = (Book) itemRepository.findOne(itemId);
        book.update(form);
    }
}
