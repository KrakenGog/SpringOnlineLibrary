package com.onlib.core.dto;

import java.util.Set;
import java.util.TreeSet;

import com.onlib.core.model.Book;

public class BookWithAuthorsDto implements Comparable<BookWithAuthorsDto> {
    public Long id;
    public String name;
    public Set<AuthorWithoutBooksDto> authors = new TreeSet<>();

    public BookWithAuthorsDto(Book book){
        id = book.getId();
        name = book.getName();
        book.getAuthors().forEach(author -> authors.add(new AuthorWithoutBooksDto(author)));
    }


    @Override
    public int compareTo(BookWithAuthorsDto arg0) {
        return id.compareTo(arg0.id);
    }
}
