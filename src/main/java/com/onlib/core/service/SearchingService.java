package com.onlib.core.service;

import java.util.List;

import com.onlib.core.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlib.core.model.Book;
import com.onlib.core.repository.AuthorRepository;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.util.StringSearcher;

@Service
public class SearchingService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository repo;

    @Autowired
    private AuthorRepository authorRepository;

    //@Transactional
    public List<Book> SearchBooks(StringSearcher searcher, String query){
        return searcher.Search(bookRepository.findAll(), query, Book::getName);
    }

    //@Transactional
    public List<Author> searchAuthors(StringSearcher searcher, String query){
        return searcher.Search(authorRepository.findAll(), query, Author::getName);
    }
}
