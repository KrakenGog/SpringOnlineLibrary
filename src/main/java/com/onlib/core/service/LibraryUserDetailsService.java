package com.onlib.core.service;

import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class LibraryUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibraryUser> userOptional = userRepository.findByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        LibraryUser user = userOptional.get();
        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();

    }
}

