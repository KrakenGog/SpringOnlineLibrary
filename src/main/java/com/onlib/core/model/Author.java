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
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    public Author(){}

    public Author(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "books_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "authors_id", referencedColumnName = "id"
            )
    )
    private List<Book> books = new ArrayList<>();
}