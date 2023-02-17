package com.sandro.jpashop.service;

import com.sandro.jpashop.controller.BookForm;
import com.sandro.jpashop.domain.item.Book;
import com.sandro.jpashop.domain.item.Item;
import com.sandro.jpashop.repository.ItemRepository;
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

    @Transactional
    public void updateItem(BookForm form) {
        Book book = (Book) itemRepository.findOne(form.getId());
        book.update(form);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
