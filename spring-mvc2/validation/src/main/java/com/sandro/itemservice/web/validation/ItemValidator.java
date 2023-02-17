package com.sandro.itemservice.web.validation;

import com.sandro.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        int MIN_ITEM_PRICE = 1000;
        int MAX_ITEM_PRICE = 1000000;
        int MAX_ITEM_QUANTITY = 9999;
        int TOTAL_PRICE_MIN = 10000;

        Item item = (Item) target;

        // 1. 상품명은 필수
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        // 2. 가격
        if (item.getPrice() == null || item.getPrice() < MIN_ITEM_PRICE || item.getPrice() > MAX_ITEM_PRICE) {
            errors.rejectValue("price", "range",
                    new Object[]{MIN_ITEM_PRICE, MAX_ITEM_PRICE}, null);
        }
        // 3. 수량
        if (item.getQuantity() == null || item.getQuantity() > MAX_ITEM_QUANTITY) {
            errors.rejectValue("quantity", "max",
                    new Object[]{MAX_ITEM_QUANTITY}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < TOTAL_PRICE_MIN) {
                errors.reject("totalPriceMin",
                        new Object[]{TOTAL_PRICE_MIN, resultPrice}, null);
            }
        }
    }
}
