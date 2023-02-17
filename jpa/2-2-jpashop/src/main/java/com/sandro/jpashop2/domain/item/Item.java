package com.sandro.jpashop2.domain.item;

import com.sandro.jpashop2.controller.BookForm;
import com.sandro.jpashop2.domain.Category;
import com.sandro.jpashop2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private final List<Category> categories = new ArrayList<>();

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item(BookForm form) {
        if (form.getId() != null) {
            this.id = form.getId();
        }
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
    }

    //== 비즈니스 로직 추가 ==//
    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        stockQuantity = restStock;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    private void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void update(BookForm form) {
        setName(form.getName());
        setPrice(form.getPrice());
        setStockQuantity(form.getStockQuantity());
    }
}
