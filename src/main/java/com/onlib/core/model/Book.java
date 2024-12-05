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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();
        

    public Book(){}

    public Book(String name) {
        this.name = name;
    }

    public void addAuthor(Author author){
        authors.add(author);
    }
}
