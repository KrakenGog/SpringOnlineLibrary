package com.onlib.core;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.onlib.core.model.Book;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.model.Rating;
import com.onlib.core.model.Review;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.RatingRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testAddReview_Success() throws NotFoundException, ConstraintViolationException {
        Long userId = 1L;
        Long bookId = 1L;
        String text = "Great book!";
        Long score = 85L;

        Book book = new Book();
        LibraryUser libraryUser = new LibraryUser();
        Rating rating = new Rating(score);
        Review review = new Review(libraryUser, text, rating);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(libraryUser));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        reviewService.addReview(userId, bookId, text, score);

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(reviewRepository, times(1)).save(any(Review.class));
        assertTrue(book.getReviews().contains(review));
    }

    @Test
    @Transactional
    public void testAddReview_BookNotFound() {
        Long userId = 1L;
        Long bookId = 1L;
        String text = "Great book!";
        Long score = 85L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            reviewService.addReview(userId, bookId, text, score);
        });

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, never()).findById(userId);
        verify(ratingRepository, never()).save(any(Rating.class));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    @Transactional
    public void testAddReview_UserNotFound() {
        Long userId = 1L;
        Long bookId = 1L;
        String text = "Great book!";
        Long score = 85L;

        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            reviewService.addReview(userId, bookId, text, score);
        });

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(ratingRepository, never()).save(any(Rating.class));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    @Transactional
    public void testAddReview_InvalidScore() {
        Long userId = 1L;
        Long bookId = 1L;
        String text = "Great book!";
        Long score = 150L; // Invalid score

        Book book = new Book();
        LibraryUser libraryUser = new LibraryUser();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(libraryUser));

        assertThrows(ConstraintViolationException.class, () -> {
            reviewService.addReview(userId, bookId, text, score);
        });

        verify(bookRepository, times(1)).findById(bookId);
        verify(userRepository, times(1)).findById(userId);
        verify(ratingRepository, never()).save(any(Rating.class));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    @Transactional
    public void testDeleteReview_Success() throws NotFoundException {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        reviewService.deleteReview(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    @Transactional
    public void testDeleteReview_NotFound() {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            reviewService.deleteReview(reviewId);
        });

        verify(reviewRepository, never()).deleteById(reviewId);
    }
}
