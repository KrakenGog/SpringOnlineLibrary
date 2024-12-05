package com.onlib.core.controller;

import com.onlib.core.dto.ReviewWithoutBookDto;
import com.onlib.core.model.Book;
import com.onlib.core.model.User;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/getBookReviews")
    public ResponseEntity<List<ReviewWithoutBookDto>> getBookReviews(@RequestParam Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book.getReviews().stream()
                .map(ReviewWithoutBookDto::new)
                .toList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/addBookReview")
    public void addBookReview(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam String reviewText
    ) {
        reviewService.addReview(reviewText, userId, bookId);
    }

    @DeleteMapping("/deleteBookReview")
    public void deleteBookReview(@RequestParam Long id) {
        reviewService.deleteReview(id);
    }
}
