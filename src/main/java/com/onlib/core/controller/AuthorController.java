package com.onlib.core.controller;

import com.onlib.core.dto.AuthorWithBooksDto;
import com.onlib.core.dto.AuthorWithoutBooksDto;
import com.onlib.core.repository.AuthorRepository;
import com.onlib.core.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/getAuthorInfo")
    public ResponseEntity<AuthorWithBooksDto> getAuthorInfo(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(
                    authorService.getAuthorWithBooksDto(id),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
