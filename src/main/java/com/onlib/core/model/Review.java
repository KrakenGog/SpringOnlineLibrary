package com.onlib.core.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
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
    @JoinColumn(name = "mark_id", referencedColumnName = "id")
    private Mark mark;

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
     * @param mark
     * @throws ConstraintViolationException mark is out of range(0, 100)
     * @throws ConstraintViolationException text is longer than 500 symbols
     */
    public Review(LibraryUser libraryUser, Book book, String text, Mark mark)
//        throws ConstraintViolationException
    {
        setLibraryUser(libraryUser);
        setBookId(book.getId());
        setText(text);
        setMark(mark);
        setDate(LocalDateTime.now());
    }
}
