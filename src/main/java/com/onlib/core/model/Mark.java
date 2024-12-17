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
    @Column(name = "id")
    private Long id;

//    @Min(value = 0L)
//    @Max(value = 100L)
    @Column(name = "value")
    private Long value;

//    /**
//     * @param value must be in range(0L, 100L)
//     * @throws ConstraintViolationException if markValue is out of range(0L, 100L)
//     */
//    public void setValue(Long value) throws ConstraintViolationException {
//        if (value < 0L || value > 100L) {
//            throw new ConstraintViolationException(null);
//        }
//        this.value = value;
//    }

    public Mark() {}

    /**
     * @param value must be in range(0L, 100L)
//     * @throws ConstraintViolationException if markValue is out of range(0L, 100L)
     */
    public Mark(Long value) /*throws ConstraintViolationException*/ {
        setValue(value);
    }
}
