package com.onlib.core.service;

import java.io.IOException;
import java.util.Optional;

import com.onlib.core.dto.BookWithAuthorsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
    private IBookFileProvider bookFileProvider;

    @Transactional
    public void AddBook(String name, String description, Author[] authors, byte[] epubFile) throws IOException {
        Book book = bookRepository.save(new Book(name, description));
        bookFileProvider.saveEpubFile(epubFile, book.getId());
        for (var author : authors) {
            Author added = authorRepository.save(author);
            book.addAuthor(added);
        }
    }

    @Transactional
    public BookWithAuthorsDto getBookWithAuthors(Long id) throws NotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent())
            return new BookWithAuthorsDto(book.get());
        else
            throw new NotFoundException();
    }
}
