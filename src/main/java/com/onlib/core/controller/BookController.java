package com.onlib.core.controller;

import org.springframework.web.bind.annotation.RestController;

import com.onlib.core.model.Book;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BookController {
    @Autowired
    private SearchingService searchingService;

    @GetMapping("/searchBooks")
    public List<Book> searchBooks(@RequestParam String query) {
        return searchingService.SearchBooks(new SimpleStringSearcher(), query);
    }

}
