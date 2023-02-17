package com.sandro.jpashop.domain.item;

import com.sandro.jpashop.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;

    public static Book createBook(BookForm form) {
        // refector : setter 사용하지 않기

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        return book;
    }

    public void update(BookForm form) {
        this.setName(form.getName());
        this.setPrice(form.getPrice());
        this.setStockQuantity(form.getStockQuantity());
        this.author = form.getAuthor();
        this.isbn = form.getIsbn();
    }
}
