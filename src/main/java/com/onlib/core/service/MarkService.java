package com.onlib.core.service;

import com.onlib.core.model.Mark;
import com.onlib.core.repository.MarkRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {
    @Autowired
    MarkRepository markRepository;

    /**
     * @param markValue must be in range(0L, 100L)
     * @throws ConstraintViolationException if markValue is out of range(0L, 100L)
     */
    @Transactional
    public void addMark(Long markValue) throws ConstraintViolationException {
        markRepository.save(new Mark(markValue));
    }
}
