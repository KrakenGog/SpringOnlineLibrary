package com.onlib.core.dto;

import java.util.Set;
import java.util.TreeSet;

import com.onlib.core.model.Book;

public class BookInfoDto implements Comparable<BookInfoDto> {
    public Long id;
    public String name;
    public String description;
    public Set<AuthorWithoutBooksDto> authors = new TreeSet<>();
    public Set<ReviewDto> reviews = new TreeSet<>();

    public BookInfoDto(Book book){
        id = book.getId();
        name = book.getName();
        description = book.getDescription();
        book.getAuthors().forEach(author -> authors.add(new AuthorWithoutBooksDto(author)));
        book.getReviews().forEach(review -> reviews.add(new ReviewDto(review)));
    }

    @Override
    public int compareTo(BookInfoDto arg0) {
        return id.compareTo(arg0.id);
    }
}
