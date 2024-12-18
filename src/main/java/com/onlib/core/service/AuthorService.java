package com.onlib.core.service;

import com.onlib.core.dto.AuthorWithBooksDto;
import com.onlib.core.model.Author;
import com.onlib.core.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public Author getAuthor(Long id) throws NotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent())
            return author.get();
        else
            throw new NotFoundException();
    }

    public List<Author> getAuthorsWithIds(List<Long> ids) throws NotFoundException {
        List<Author> res = new ArrayList<>();

        for (var id : ids) {
            Optional<Author> author = authorRepository.findById(id);

            if (author.isPresent())
                res.add(author.get());
            else
                throw new NotFoundException();
        }

        return res;
    }

    public void addAuthor(String name) {
        authorRepository.save(new Author(name));
    }
}
