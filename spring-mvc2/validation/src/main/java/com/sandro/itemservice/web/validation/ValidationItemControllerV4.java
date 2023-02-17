package com.sandro.itemservice.web.validation;

import com.sandro.itemservice.domain.item.Item;
import com.sandro.itemservice.domain.item.ItemRepository;
import com.sandro.itemservice.web.validation.form.ItemSaveForm;
import com.sandro.itemservice.web.validation.form.ItemUpdateForm;
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
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("form", new ItemSaveForm());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("form") ItemSaveForm form,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes)
    {
        // 특정 필드가 아닌 복합 룰 검증
        validateObjectError(form.getPrice(), form.getQuantity(), bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "validation/v4/addForm";
        }

        Item item = new Item(form.getItemName(), form.getPrice(), form.getQuantity());
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("form", new ItemUpdateForm(item));
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @Validated @ModelAttribute("form") ItemUpdateForm form,
                       BindingResult bindingResult)
    {
        validateObjectError(form.getPrice(), form.getQuantity(), bindingResult);

        if (bindingResult.hasErrors()) {
            return "validation/v4/editForm";
        }

        Item UpdateParam = new Item(form.getId(), form.getItemName(), form.getPrice(), form.getQuantity());
        itemRepository.update(itemId, UpdateParam);
        return "redirect:/validation/v4/items/{itemId}";
    }
    
    private static void validateObjectError(Integer price, Integer quantity, BindingResult bindingResult) {
        int TOTAL_PRICE_MIN = 10000;

        if (price != null && quantity != null) {
            int resultPrice = price * quantity;
            if (resultPrice < TOTAL_PRICE_MIN) {
                bindingResult.reject(
                        "totalPriceMin",
                        new Object[]{TOTAL_PRICE_MIN, resultPrice},
                        null
                );
            }
        }
    }
}
