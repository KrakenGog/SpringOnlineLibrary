package com.onlib.core.dto;

import com.onlib.core.model.Review;
import com.onlib.core.model.User;

public class ReviewWithoutBookDto {
    Long id;
    String text;
    User user;

    public ReviewWithoutBookDto(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.user = review.getUser();
    }
}
