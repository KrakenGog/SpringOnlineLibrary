package com.onlib.core.controller;

import com.onlib.core.dto.ReviewWithoutBookDto;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/getBookReviews")
    public ResponseEntity<List<ReviewWithoutBookDto>> getBookReviews(
            @RequestParam Long bookId
    ) {
        return new ResponseEntity<>(
                reviewService.getReviewsWithoutBookByBookId(bookId),
                HttpStatus.OK
        );
    }

    @PostMapping("/addBookReview")
    public ResponseEntity<Object> addBookReview(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam String text
    ) {
        try {
            reviewService.addReview(text, userId, bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteBookReview")
    public ResponseEntity<Object> deleteBookReview(
            @RequestParam Long id
    ) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
