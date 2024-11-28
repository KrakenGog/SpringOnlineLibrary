package com.onlib.core.dto;

import com.onlib.core.model.Author;

public class AuthorWithoutBooksDto implements Comparable<AuthorWithoutBooksDto> {
    public Long id;
    public String name;

    public AuthorWithoutBooksDto(Author author){
        name = author.getName();
        id = author.getId();
    }

    @Override
    public int compareTo(AuthorWithoutBooksDto arg0) {
        return id.compareTo(arg0.id);
    }
}
