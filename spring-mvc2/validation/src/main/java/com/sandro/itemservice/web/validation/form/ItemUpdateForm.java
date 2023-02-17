package com.sandro.itemservice.web.validation.form;

import com.sandro.itemservice.domain.item.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemUpdateForm {

    @NotNull
    private Long id;
    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    private Integer quantity;

    public ItemUpdateForm(Item item) {
        id = item.getId();
        itemName = item.getItemName();
        price = item.getPrice();
        quantity = item.getQuantity();
    }
}
