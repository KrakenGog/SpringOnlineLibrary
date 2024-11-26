package com.onlib.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.onlib.core.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    
}