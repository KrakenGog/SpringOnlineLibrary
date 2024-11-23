package com.onlib.core.controller;

import org.springframework.web.bind.annotation.RestController;

import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;

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

    // @GetMapping("/getBookEpubFile")
    // public ResponseEntity<Resource> getMethodName(@RequestParam long id) throws
    // RuntimeException, IOException {
    // Book book = getBook(id);
    //
    // Resource file = new ClassPathResource("static/books/" +
    // book.getEpubFileName());
    // return ResponseEntity.ok()
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;
    // filename=\"file.epub\"")
    // .contentType(MediaType.APPLICATION_OCTET_STREAM)
    // .body(file);
    // }
    @GetMapping("/getBookEpubFile.epub")
    public ResponseEntity<byte[]> getMethodName(@RequestParam long id) throws RuntimeException, IOException {
        Book book = getBook(id);
        File file = ResourceUtils.getFile("classpath:static/books/" + book.getEpubFileName());
        InputStream in = new FileInputStream(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/epub+zip"));
        byte[] bytes = in.readAllBytes();
        in.close();
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
