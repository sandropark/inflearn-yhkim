package com.sandro.jpashop2.controller;

import com.sandro.jpashop2.domain.item.Book;
import com.sandro.jpashop2.domain.item.Item;
import com.sandro.jpashop2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String createItem(BookForm form) {
        Book book = new Book(form);
        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book book = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm(book);
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, BookForm form) {
        itemService.updateItem(itemId, form);
        return "redirect:/items";
    }
}
