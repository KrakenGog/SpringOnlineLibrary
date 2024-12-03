package com.onlib.core.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDetailsManager userDetailsManger;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(String username, String password) {
        userDetailsManger.createUser(
                User.withUsername(username)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
    }
}