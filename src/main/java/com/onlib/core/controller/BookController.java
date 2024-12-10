package com.onlib.core.controller;

import com.onlib.core.service.BookService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.RestController;

import com.onlib.core.dto.BookWithAuthorsDto;
import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.IBookFileProvider;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;

import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;

@RestController
public class BookController {
    @Autowired
    private SearchingService searchingService;

    @Autowired
    private IBookFileProvider bookFileProvider;

    @Autowired
    private BookService bookService;

    @GetMapping("/searchBooks")
    public List<BookWithAuthorsDto> searchBooks(
            @RequestParam String query
    ) {
        if (Objects.equals(query, "undefined"))
            query = "";

        return searchingService.SearchBooks(new SimpleStringSearcher(), query)
                .stream()
                .map(BookWithAuthorsDto::new)
                .toList();
    }

    @GetMapping("/getBookInfo")
    public ResponseEntity<BookWithAuthorsDto> getBook(
            @RequestParam long id
    ) {
        try {
            return new ResponseEntity<>(
                    bookService.getBookWithAuthors(id),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBookEpubFile.epub")
    public ResponseEntity<byte[]> getBookEpubFile(
            @RequestParam long id
    ) throws RuntimeException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/epub+zip"));
        
        try {
            return new ResponseEntity<>(bookFileProvider.getEpubFile(id), headers, HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
