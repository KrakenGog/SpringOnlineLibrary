package com.onlib.core.service;

import com.onlib.core.model.Book;
import com.onlib.core.model.Review;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /// Добавляется ли автоматически review в book?
    @Transactional
    public void addReview(String text, Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new RuntimeException("Cant find book with id " + bookId);
        }

        LibraryUser libraryUser = userRepository.findById(userId).orElse(null);
        if (libraryUser == null) {
            throw new RuntimeException("Cant find user with id " + userId);
        }

        Review review = reviewRepository.save(new Review(text));
        review.setLibraryUser(libraryUser);
        review.setBookId(bookId);
        book.addReview(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
