package com.onlib.core.service;

import com.onlib.core.model.Book;
import com.onlib.core.model.Review;
import com.onlib.core.model.User;
import com.onlib.core.repository.ReviewRepository;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void addReview(String text, User user, Book book) {
        Review review = new Review(text);
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
