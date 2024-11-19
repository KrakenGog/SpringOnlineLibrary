package com.onlib.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlib.core.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    
}