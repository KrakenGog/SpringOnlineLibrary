package com.onlib.core.service;

import com.onlib.core.dto.ReviewWithoutBookDto;
import com.onlib.core.model.Book;
import com.onlib.core.model.Review;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addReview(String text, Long userId, Long bookId) throws NotFoundException {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new NotFoundException();
        }

        LibraryUser libraryUser = userRepository.findById(userId).orElse(null);
        if (libraryUser == null) {
            throw new NotFoundException();
        }

        Review review = reviewRepository.save(new Review(text));
        review.setLibraryUser(libraryUser);
        review.setBookId(bookId);
        book.addReview(review);
    }

    @Transactional
    public void deleteReview(Long id) throws NotFoundException {
        if (reviewRepository.existsById(id))
            reviewRepository.deleteById(id);
        else
            throw new NotFoundException();
    }

    @Transactional
    public List<ReviewWithoutBookDto> getReviewsWithoutBookByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .map(ReviewWithoutBookDto::new)
                .toList();
    }
}
