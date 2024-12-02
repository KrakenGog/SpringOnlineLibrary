package com.onlib.core.controller;

import com.onlib.core.dto.ReviewWithoutBookDto;
import com.onlib.core.model.Book;
import com.onlib.core.model.User;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/getBookReviews")
    public List<ReviewWithoutBookDto> getBookReviews(@RequestParam Book book) {
        return book.getReviews().stream()
                .map(ReviewWithoutBookDto::new)
                .toList();
    }

    @GetMapping("/addBookReview")
    public void addBookReview(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam String reviewText
    ) {
        reviewService.addReview(reviewText, userId, bookId);
    }

    @GetMapping("/deleteBookReview")
    public void deleteBookReview(@RequestParam Long id) {
        reviewService.deleteReview(id);
    }
}
