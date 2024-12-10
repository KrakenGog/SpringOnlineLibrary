package com.onlib.core.dto;

import com.onlib.core.model.Book;

public class BookWithOnlyNameDto {
    public Long id;
    public String name;

    public BookWithOnlyNameDto() {}
    public BookWithOnlyNameDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
    }
}
