package com.onlib.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import lombok.Data;


@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reviews_id", referencedColumnName = "id")
    private List<Review> reviews = new ArrayList<>();

    public Book(){}

    public Book(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void addReview(Review review){
        reviews.add(review);
    }
}
