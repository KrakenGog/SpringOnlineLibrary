package com.onlib.core.repository;

import com.onlib.core.model.Review;
import jakarta.persistence.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
