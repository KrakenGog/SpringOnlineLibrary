package com.onlib.core.dto;

import java.util.Set;
import java.util.TreeSet;

import com.onlib.core.model.Book;

public class BookWithAuthorsDto implements Comparable<BookWithAuthorsDto> {
    public Long id;
    public String name;
    public Set<AuthorWithoutBooksDto> authors;


    public BookWithAuthorsDto(Book book){
        authors = new TreeSet<>();
        name = book.getName();
        id = book.getId();
        for (var author : book.getAuthors()) {
            authors.add(new AuthorWithoutBooksDto(author));
        }
    }


    @Override
    public int compareTo(BookWithAuthorsDto arg0) {
        return id.compareTo(arg0.id);
    }
}
