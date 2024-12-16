package com.onlib.core.repository;

import com.onlib.core.model.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository<Mark, String> {
}
