package com.sandro.itemservice.web.validation;

import com.sandro.itemservice.domain.item.Item;
import com.sandro.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item,
                          RedirectAttributes redirectAttributes,
                          Model model)
    {
        Map<String, String> errors = validation(item);

        // 검증에 실패하면 다시 입력 폼으로
        if (hasError(errors)) {
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    private static Map<String, String> validation(Item item) {
        // 검증 오류 결과 보관
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        // 1. 상품명은 필수, 공백 X
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        // 2. 가격
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격 허용 범위는 1000 ~ 1,000,000 입니다.");
        }
        // 3. 수량
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "최대 수량은 9999 입니다.");
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "경고! 가격 x 수량은 10,000원 이상이어야 합니다. 현재 값 : " + resultPrice + "원");
            }
        }
        return errors;
    }

    private static boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {
        // 검증 로직
        Map<String, String> errors = validation(item);

        // 검증에 실패하면 다시 입력 폼으로
        if (hasError(errors)) {
            model.addAttribute("errors", errors);
            return "validation/v1/editForm";
        }

        // 성공 로직
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

