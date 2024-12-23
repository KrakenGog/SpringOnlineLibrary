package com.onlib.core.controller;

import com.onlib.core.dto.ReviewDto;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;
    private UserController userController;

    @GetMapping("/getBookReviews")
    public ResponseEntity<List<ReviewDto>> getBookReviews(
            @RequestParam Long bookId
    ) {
        try {
            return new ResponseEntity<>(
                    reviewService.getReviewsWithoutBookByBookId(bookId),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/addBookReview")
    public ResponseEntity<Object> addBookReview(
            @RequestParam Long bookId,
            @RequestParam String text,
            @RequestParam Long rating
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<LibraryUser> user = userRepository.findByName(userName);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.get().getId();

        try {
            reviewService.addReview(userId, bookId, text, rating);
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
