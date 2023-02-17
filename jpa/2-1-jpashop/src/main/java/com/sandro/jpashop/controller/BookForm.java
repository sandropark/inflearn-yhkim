package com.sandro.jpashop.controller;

import com.sandro.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    // refactor : validation로직 추가
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public static BookForm createForm(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());
        return form;
    }
}
