package com.onlib.core;

import com.onlib.core.model.Book;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.model.Review;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;
import jakarta.validation.ConstraintViolationException;

import org.apache.catalina.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addReviewTest() {
        Book book = new Book();
        LibraryUser user = new LibraryUser();

        String text = "text";
        Long mark = 0L;
        Long bookId = book.getId();
        Long userId = user.getId();

        LocalDateTime dateExpected = LocalDateTime.now();
        long maxDifferenceInMinutes = 1L;
        LocalDateTime dateActual;
        double differenceInMinutes;

        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.save(reviewCaptor.capture()))
                .thenAnswer(invocation -> reviewCaptor.getValue());


        assertDoesNotThrow(() -> reviewService.addReview(userId, bookId, text, mark));


        Review result = reviewCaptor.getValue();

        assertEquals("text field error", result.getText(), text);
        assertEquals("mark field error", result.getMark(), mark);

        dateActual = result.getDate();
        differenceInMinutes = ChronoUnit.MINUTES.between(dateExpected, dateActual);
        assertTrue(
                "date field error",
                differenceInMinutes <= maxDifferenceInMinutes
        );

        assertEquals("bookId field error", result.getBookId(), bookId);
        assertEquals("user field error", result.getLibraryUser(), user);
        assertTrue("chaining review to book error", book.getReviews().contains(result));
    }

    @Test
    public void addReviewThrowsTest() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(new Book()));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new LibraryUser()));
        when(reviewRepository.save(any(Review.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Проверка, что выбрасывается NotFoundException, если книга не найдена
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> reviewService.addReview(1L, 1L, "text", 1L)
        );

        // Проверка, что метод не выбрасывает исключение, если книга найдена
         when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(new Book()));
         assertDoesNotThrow(
                 () -> reviewService.addReview(1L, 1L, "text", 1L)
         );

         // Проверка, что выбрасывается NotFoundException, если пользователь не найден
         when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
         assertThrows(
                 NotFoundException.class,
                 () -> reviewService.addReview(1L, 1L, "text", 1L)
         );

         // Проверка, что метод не выбрасывает исключение, если пользователь найден
         when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new LibraryUser()));
         assertDoesNotThrow(
                 () -> reviewService.addReview(1L, 1L, "text", 1L)
         );

         // Проверка, что выбрасывается ConstraintViolationException, если оценка вне допустимого диапазона
         assertThrows(
                 ConstraintViolationException.class,

                 () -> reviewService.addReview(1L, 1L, "text", -5L)
         );
         assertThrows(
                 ConstraintViolationException.class,
                 () -> reviewService.addReview(1L, 1L, "text", 105L)
         );
    }
}

