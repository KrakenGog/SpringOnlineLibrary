package com.onlib.core.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private LibraryUser libraryUser;

    @Column(name = "book_id")
    private Long bookId;

    public Review() {
        setDate(LocalDateTime.now());
    }

    /**
     * @param text
     * @param book
     * @throws ConstraintViolationException mark is out of range(0, 100)
     * @throws ConstraintViolationException text is longer than 500 symbols
     */
    public Review(String text, Book book, LibraryUser libraryUser)
        throws ConstraintViolationException
    {
        setText(text);
        setDate(LocalDateTime.now());
        setLibraryUser(libraryUser);
        setBookId(book.getId());
    }
}
