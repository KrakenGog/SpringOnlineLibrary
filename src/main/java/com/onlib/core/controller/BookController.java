package com.onlib.core.controller;

import com.onlib.core.service.AuthorService;
import com.onlib.core.service.BookService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlib.core.dto.BookWithAuthorsDto;
import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.IBookFileProvider;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.text.ChangedCharSetException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BookController {
    @Autowired
    private SearchingService searchingService;

    @Autowired
    private IBookFileProvider bookFileProvider;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @GetMapping("/searchBooks")
    public List<BookWithAuthorsDto> searchBooks(
            @RequestParam String query) {
        if (Objects.equals(query, "undefined"))
            query = "";

        return searchingService.SearchBooks(new SimpleStringSearcher(), query)
                .stream()
                .map(BookWithAuthorsDto::new)
                .toList();
    }

    @GetMapping("/getBookInfo")
    public ResponseEntity<BookWithAuthorsDto> getBook(
            @RequestParam long id) {
        try {
            return new ResponseEntity<>(
                    new BookWithAuthorsDto(bookService.getBookWithAuthors(id)),
                    HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBookEpubFile.epub")
    public ResponseEntity<byte[]> getBookEpubFile(
            @RequestParam long id) throws RuntimeException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/epub+zip"));

        try {
            return new ResponseEntity<>(bookFileProvider.getEpubFile(id), headers, HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestParam String name, @RequestParam String description,
            @RequestParam List<Long> authorsId, @RequestParam MultipartFile file) {
        try {
            authorService.getAuthor(1L);
            bookService.AddBook(name, description, authorService.getAuthorsWithIds(authorsId), file.getBytes());
        } catch (IOException ex) {
            return new ResponseEntity<>("Error while saving file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (NotFoundException ex){
            return new ResponseEntity<>("Cant find author", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
