package com.sandro.itemservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void update(Item updateParam) {
        itemName = updateParam.getItemName();
        price = updateParam.getPrice();
        quantity = updateParam.getQuantity();
    }
}
