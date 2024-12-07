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

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void addReview(String text, Long userId, Long bookId) {
        LibraryUser libraryUser = userRepository.findById(userId).orElse(null);
        if (libraryUser == null) {
            throw new RuntimeException("user not found");
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new RuntimeException("book not found");
        }

        Review review = new Review(text);
        //Review review = reviewRepository.save(new Review(text));
        review.setLibraryUser(libraryUser);
        review.setBook(book);

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) {
            throw new RuntimeException("review not found");
        }
        reviewRepository.delete(review);
    }
}
