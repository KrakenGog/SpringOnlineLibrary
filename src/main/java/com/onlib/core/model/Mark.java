package com.onlib.core.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 0L)
    @Max(value = 100L)
    @Column(name = "value")
    private Long value;

    public void setValue(Long value) throws ConstraintViolationException {
        if (value < 0L || value > 100L) {
            throw new ConstraintViolationException(null);
        }
        this.value = value;
    }

    public Mark() {}

    public Mark(Long value) {
        setValue(value);
    }
}
