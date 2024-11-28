package com.onlib.core.controller;

import org.springframework.web.bind.annotation.RestController;

import com.onlib.core.dto.BookWithAuthorsDto;
import com.onlib.core.model.Book;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.BookFileProvider;
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

    @Autowired
    private BookFileProvider bookFileProvider;

    @GetMapping("/searchBooks")
    public List<BookWithAuthorsDto> searchBooks(@RequestParam String query) {
        return searchingService.SearchBooks(new SimpleStringSearcher(), query).stream().map(x -> new BookWithAuthorsDto(x)).toList();
    }

    @GetMapping("/getBookInfo")
    public BookWithAuthorsDto getBook(@RequestParam long id) {
        Optional<Book> res = bookRepository.findById(id);
        if (res.isPresent())
            return new BookWithAuthorsDto(res.get());
        else
            throw new RuntimeException("Cant find book with id: " + id);
    }

    @GetMapping("/getBookEpubFile.epub")
    public ResponseEntity<byte[]> getBookEpubFile(@RequestParam long id) throws RuntimeException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/epub+zip"));
       
        return new ResponseEntity<>(bookFileProvider.getEpubFile(id), headers, HttpStatus.OK);
    }

}
