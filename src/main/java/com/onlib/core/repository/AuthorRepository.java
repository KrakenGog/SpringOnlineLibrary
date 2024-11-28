package com.onlib.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlib.core.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    
}