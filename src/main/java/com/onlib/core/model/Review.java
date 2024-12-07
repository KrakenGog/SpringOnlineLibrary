package com.onlib.core.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private LibraryUser libraryUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;

    public Review() {}

    public Review(String text) {
        this.text = text;
    }
}
