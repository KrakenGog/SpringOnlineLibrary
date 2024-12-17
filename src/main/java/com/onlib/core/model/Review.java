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
    @Column(name = "id")
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private LibraryUser libraryUser;

    @Column(name = "book_id")
    private Long bookId;

    public Review() {
        setDate(LocalDateTime.now());
    }

    /**
     * @param book
     * @param text
     * @param rating
     * @throws ConstraintViolationException mark is out of range(0, 100)
     * @throws ConstraintViolationException text is longer than 500 symbols
     */
    public Review(LibraryUser libraryUser, Book book, String text, Rating rating)
//        throws ConstraintViolationException
    {
        setLibraryUser(libraryUser);
        setBookId(book.getId());
        setText(text);
        setRating(rating);
        setDate(LocalDateTime.now());
    }
}
