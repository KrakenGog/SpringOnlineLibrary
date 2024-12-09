package com.onlib.core.service;

import com.onlib.core.dto.AuthorWithBooksDto;
import com.onlib.core.model.Author;
import com.onlib.core.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public AuthorWithBooksDto getAuthorWithBooksDto(Long id) throws NotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent())
            return new AuthorWithBooksDto(author.get());
        else
            throw new NotFoundException();
    }
}
