package com.onlib.core.dto;

import com.onlib.core.model.Author;

import java.util.Set;
import java.util.TreeSet;

public class AuthorWithBooksDto {
    public Long id;
    public String name;
    public Set<BookWithOnlyNameDto> books = new TreeSet<>();

    public AuthorWithBooksDto() {}
    public AuthorWithBooksDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        author.getBooks().forEach(book -> books.add(new BookWithOnlyNameDto(book)));
    }
}
