package com.onlib.core.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Min(value = 0L)
    @Max(value = 100L)
    @Column(name = "number")
    private Long score;

    /**
     * @param score must be in range(0L, 100L)
     * @throws ConstraintViolationException if markValue is out of range(0L, 100L)
     */
    public void setScore(Long score) throws ConstraintViolationException {
        if (score < 0L || score > 100L) {
            throw new ConstraintViolationException(null);
        }
        this.score = score;
    }

    public Rating() {}

    /**
     * @param score must be in range(0L, 100L)
     * @throws ConstraintViolationException if markValue is out of range(0L, 100L)
     */
    public Rating(Long score) throws ConstraintViolationException {
        setScore(score);
    }
}
