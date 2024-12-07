package com.onlib.core.dto;

import com.onlib.core.model.Review;
import com.onlib.core.model.LibraryUser;

public class ReviewWithoutBookDto implements Comparable<ReviewWithoutBookDto> {

    public Long id;
    public String text;
    public UserWithoutPasswordDto user;

    public ReviewWithoutBookDto(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.user = new UserWithoutPasswordDto(review.getLibraryUser());
    }

    @Override
    public int compareTo(ReviewWithoutBookDto review) {
        return id.compareTo(review.id);
    }
}
