package com.onlib.core.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlib.core.model.Author;
import com.onlib.core.model.Book;
import com.onlib.core.repository.AuthorRepository;
import com.onlib.core.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookFileProvider bookFileProvider;

    @Transactional
    public void AddBook(String name, Author[] authors, byte[] epubFile) throws IOException {
        Book book = bookRepository.save(new Book(name));
        bookFileProvider.saveEpubFile(epubFile, book.getId());
        for (var author : authors) {
            Author added = authorRepository.save(author);
            
            book.addAuthor(added);
        }
    }
}
