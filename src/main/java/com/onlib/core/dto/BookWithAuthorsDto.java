package com.onlib.core.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.onlib.core.model.Book;
import com.onlib.core.model.Review;

public class BookWithAuthorsDto implements Comparable<BookWithAuthorsDto> {
    public Long id;
    public String name;
    public String description;
    public Set<AuthorWithoutBooksDto> authors = new TreeSet<>();
    public Set<ReviewWithoutBookDto> reviews = new TreeSet<>();


    public BookWithAuthorsDto(Book book){
        id = book.getId();
        name = book.getName();
        description = book.getDescription();
        book.getAuthors().forEach(author -> authors.add(new AuthorWithoutBooksDto(author)));
        book.getReviews().forEach(review -> reviews.add(new ReviewWithoutBookDto(review)));
    }


    @Override
    public int compareTo(BookWithAuthorsDto arg0) {
        return id.compareTo(arg0.id);
    }
}
