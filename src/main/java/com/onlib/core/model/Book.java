package com.onlib.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String[] authors;

    private String epubFileName;



    public Book(String name, String[] authors, String epubFileName) {
        this.name = name;
        this.authors = authors;
        this.epubFileName = epubFileName;
    }

    public Book(){}

    public Book(String name) {
        this.name = name;
    }
}
