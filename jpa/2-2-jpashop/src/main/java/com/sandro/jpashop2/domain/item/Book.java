package com.sandro.jpashop2.domain.item;

import com.sandro.jpashop2.controller.BookForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {
    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(BookForm form) {
        super(form);
        this.author = form.getAuthor();
        this.isbn = form.getIsbn();
    }

    @Override
    public void update(BookForm form) {
        super.update(form);
        setAuthor(form.getAuthor());
        setIsbn(form.getIsbn());
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
