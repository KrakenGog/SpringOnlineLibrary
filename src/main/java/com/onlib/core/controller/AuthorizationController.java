package com.onlib.core.controller;

import com.onlib.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthorizationController {
    @Autowired
    UserService userService;
    @PostMapping("/registerUser")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        userService.addUser(username, password);;
        return "/login";
    }

}
