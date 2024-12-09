package com.onlib.core.controller;

import com.onlib.core.dto.UserWithoutPasswordDto;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
