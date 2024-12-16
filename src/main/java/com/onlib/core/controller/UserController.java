package com.onlib.core.controller;

import com.onlib.core.dto.BookWithOnlyNameDto;
import com.onlib.core.dto.UserWithoutPasswordDto;
import com.onlib.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public ResponseEntity<UserWithoutPasswordDto> getUserInfo(
            @RequestParam Long id
    ) {
        try {
            return new ResponseEntity<>(
                    userService.getUserWithoutPassword(id),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserFavouriteBooks")
    public ResponseEntity<List<BookWithOnlyNameDto>> getUserFavouriteBooks(
            @RequestParam Long id
    ) {
        try {
            return new ResponseEntity<>(
                    userService.getFavouriteBooksWithOnlyName(id),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/putBookToFavourites")
    public ResponseEntity<Object> putBookToFavourites(
            @RequestParam Long userId,
            @RequestParam Long bookId
    ) {
        try {
            userService.putBookToFavourites(userId, bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/removeBookFromFavourites")
    public ResponseEntity<Object> removeBookFromFavourites(
            @RequestParam Long userId,
            @RequestParam Long bookId
    ) {
        try {
            userService.removeBookFromFavourites(userId, bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
