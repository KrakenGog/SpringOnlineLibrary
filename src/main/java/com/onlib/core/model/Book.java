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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "authors_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "books_id", referencedColumnName = "id"
            )
    )
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

    public void addReview(Review review) {
        reviews.add(review);
    }
}
