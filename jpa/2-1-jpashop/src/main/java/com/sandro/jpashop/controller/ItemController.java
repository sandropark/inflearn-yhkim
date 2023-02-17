package com.sandro.jpashop.controller;

import com.sandro.jpashop.domain.item.Book;
import com.sandro.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        itemService.saveItem(Book.createBook(form));
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String List(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemFrom(@PathVariable("itemId") Long itemId, Model model) {
        // 1. 쿼리 데이터로 넘어온 id로 아이템을 찾는다.
        Book item = (Book) itemService.findOne(itemId);
        // 2. 찾은 아이템 객체를 form객체로 만든다.
        BookForm form = BookForm.createForm(item);
        // 3. 폼 객체를 model에 넘긴다.
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@ModelAttribute("form") BookForm form) {
        /**
         * 지금 업데이트를 하기 위해서 book객체를 생성하고 form의 데이터를 넘겨줬다.
         * 이 book객체는 새로 생성한 객체이기는 하지만 form에는 id가 있기 때문에 준영속 엔티티이다.
         * 준영속 엔티티는 jpa가 자동으로 변화를 감지해서 업데이트하지 않는다.
         * 업데이트를 하는 방법은 2가지가 있다.
         * 1. 자동 변화 감지
         * 2. 병합
         *
         * 1. 자동 변화 감지
         * form에서 넘어온 id를 가지고 db에서 영속성 객체를 가져와서 form의 값을 객체에 넣어준다.
         *
         * 2. 병합
         * 병합은 jpa에서 자동으로 영속성 객체를 가져와서 준영속성 객체의 값으로 업데이트한다.
         *
         * 우리는 변화감지를 사용해야 한다.
         * 병합은 해당 객체의 모든 값을 업데이트하기 때문에 업데이트 시 모든 값에 대해서 신경쓰지 않으면
         * 잘못해서 null로 데이터가 사라질 수도 있다.
         * 변화감지는 우리가 수정하고자 하는 필드만 선택적으로 수정할 수 있기 때문에 위혐요인이 상대적으로 적다.
         * 따라서 변화감지를 사용한다.
         * 실무에서는 업데이트 가능한 필드를 제한하기 때문에 merge의 리스크가 더 커진다.
         */
        itemService.updateItem(form);
        return "redirect:/items";
    }
}
