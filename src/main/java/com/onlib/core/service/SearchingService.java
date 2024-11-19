package com.onlib.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.util.StringSearcher;

@Service
public class SearchingService {
    @Autowired
    private BookRepository bookRepository;


    public List<Book> SearchBooks(StringSearcher searcher, String query){
        return searcher.Search(bookRepository.findAll(), query, Book::getName);
    }
}
