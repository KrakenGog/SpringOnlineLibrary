package com.onlib.core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<Book> favouriteBooks = new ArrayList<>();

    public LibraryUser() {}

    public LibraryUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addFavouriteBook(Book book) {
        favouriteBooks.add(book);
    }
}
