package com.onlib.core.controller;

import org.springframework.web.bind.annotation.RestController;

import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;

import io.micrometer.core.instrument.util.IOUtils;


import java.io.IOException;
import java.io.InputStream;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class BookController {
    @Autowired
    private SearchingService searchingService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/searchBooks")
    public List<Book> searchBooks(@RequestParam String query) {
        return searchingService.SearchBooks(new SimpleStringSearcher(), query);
    }

    @GetMapping("/getBookInfo")
    public Book getBook(@RequestParam long id) {
        Optional<Book> res = bookRepository.findById(id);
        if (res.isPresent())
            return res.get();
        else
            throw new RuntimeException("Cant find book with id: " + id);
    }

    @GetMapping("/getBookEpubFile")
    public ResponseEntity<byte[]> getMethodName(@RequestParam long id) throws RuntimeException, IOException {
        Book book = getBook(id);
        ClassPathResource resource = new ClassPathResource("static/books/" + book.getEpubFileName());
        InputStream in = resource.getInputStream();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + id + ".epub");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
        
        return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
    }

}
