package com.onlib.core.controller;

import com.onlib.core.dto.AuthorWithBooksDto;
import com.onlib.core.dto.AuthorWithoutBooksDto;
import com.onlib.core.dto.BookWithAuthorsDto;
import com.onlib.core.model.Author;
import com.onlib.core.repository.AuthorRepository;
import com.onlib.core.service.AuthorService;
import com.onlib.core.service.SearchingService;
import com.onlib.core.util.SimpleStringSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private SearchingService searchingService;

    @GetMapping("/getAuthorInfo")
    public ResponseEntity<AuthorWithBooksDto> getAuthorInfo(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(
                    new AuthorWithBooksDto(authorService.getAuthor(id)),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<String> addAuthor(@RequestParam String name){
        authorService.addAuthor(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchAuthors")
    public List<AuthorWithoutBooksDto> searchAuthors(@RequestParam String query){
        if (Objects.equals(query, "undefined"))
            query = "";

        return searchingService.searchAuthors(new SimpleStringSearcher(), query)
                .stream()
                .map(AuthorWithoutBooksDto::new)
                .toList();
    }
}
