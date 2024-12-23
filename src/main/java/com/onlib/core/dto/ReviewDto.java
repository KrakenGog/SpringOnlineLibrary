package com.onlib.core.dto;

import com.onlib.core.model.Review;

import java.time.format.DateTimeFormatter;

public class ReviewDto implements Comparable<ReviewDto> {

    public Long id;
    public String text;
    public String date;
    public UserWithoutPasswordDto user;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.user = new UserWithoutPasswordDto(review.getLibraryUser());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = review.getDate().format(formatter);
    }

    @Override
    public int compareTo(ReviewDto review) {
        return id.compareTo(review.id);
    }
}
