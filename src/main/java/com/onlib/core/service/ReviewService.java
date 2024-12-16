package com.onlib.core.service;

import com.onlib.core.dto.ReviewWithoutBookDto;
import com.onlib.core.model.Book;
import com.onlib.core.model.Mark;
import com.onlib.core.model.Review;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.MarkRepository;
import com.onlib.core.repository.ReviewRepository;
import com.onlib.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
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

    @Autowired
    private MarkRepository markRepository;

    /**
     * @param userId
     * @param bookId
     * @param text
     * @param markValue
     * @throws NotFoundException            can't find user or book in Database
     * @throws ConstraintViolationException markValue is out or range(0, 100)
     */
    @Transactional
    public void addReview(
            Long userId,
            Long bookId,
            String text,
            Long markValue
    )
            throws NotFoundException, ConstraintViolationException
    {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new NotFoundException();
        }

        LibraryUser libraryUser = userRepository.findById(userId).orElse(null);
        if (libraryUser == null) {
            throw new NotFoundException();
        }

        Mark mark = markRepository.save(new Mark(markValue));

        Review review = new Review(libraryUser, book, text, mark);
        reviewRepository.save(
                review
        );
        book.addReview(review);
    }

    /**
     *
     * @param id id of review to delete
     * @throws NotFoundException can't find review by id in Database
     */
    @Transactional
    public void deleteReview(Long id) throws NotFoundException {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public List<ReviewWithoutBookDto> getReviewsWithoutBookByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .map(ReviewWithoutBookDto::new)
                .toList();
    }
}
