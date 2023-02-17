package com.sandro.itemservice.web.validation;

import com.sandro.itemservice.domain.item.Item;
import com.sandro.itemservice.domain.item.ItemRepository;
import com.sandro.itemservice.domain.item.SaveCheck;
import com.sandro.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    @PostMapping("/add")
    public String addItemV2(@Validated(SaveCheck.class) @ModelAttribute Item item,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes)
    {
        // 특정 필드가 아닌 복합 룰 검증
        validateObjectError(item, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    private static void validateObjectError(Item item, BindingResult bindingResult) {
        int TOTAL_PRICE_MIN = 10000;

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < TOTAL_PRICE_MIN) {
                bindingResult.reject(
                        "totalPriceMin",
                        new Object[]{TOTAL_PRICE_MIN, resultPrice},
                        null
                );
            }
        }
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId,
                       @Validated(UpdateCheck.class) @ModelAttribute Item item,
                       BindingResult bindingResult)
    {
        validateObjectError(item, bindingResult);

        if (bindingResult.hasErrors()) {
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}
