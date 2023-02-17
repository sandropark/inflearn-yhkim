package com.sandro.itemservice.web.validation;

import com.sandro.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form,
                          BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        validateObjectError(form.getPrice(), form.getQuantity(), bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직");
        return form;
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
