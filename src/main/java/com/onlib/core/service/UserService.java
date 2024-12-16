package com.onlib.core.service;

import com.onlib.core.dto.BookWithOnlyNameDto;
import com.onlib.core.dto.UserWithoutPasswordDto;
import com.onlib.core.model.Book;
import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.BookRepository;
import com.onlib.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDetailsManager userDetailsManger;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void addUser(String username, String password) {
        userDetailsManger.createUser(
                User.withUsername(username)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
        userRepository.save(new LibraryUser(username, password));
    }

    @Transactional
    public UserWithoutPasswordDto getUserWithoutPassword(long userId) throws NotFoundException {
        Optional<LibraryUser> user = userRepository.findById(userId);
        if (user.isPresent())
            return new UserWithoutPasswordDto(user.get());
        else
            throw new NotFoundException();
    }

    @Transactional
    public List<BookWithOnlyNameDto> getFavouriteBooksWithOnlyName(Long id) throws NotFoundException {
        Optional<LibraryUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get()
                    .getFavouriteBooks()
                    .stream()
                    .map(BookWithOnlyNameDto::new)
                    .toList();
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public void putBookToFavourites(Long userId, Long bookId) throws NotFoundException {
        Optional<LibraryUser> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isPresent() && book.isPresent()) {
            user.get().getFavouriteBooks().add(book.get());
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public void removeBookFromFavourites(Long userId, Long bookId)
            throws NotFoundException, NoSuchElementException
    {
        Optional<LibraryUser> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isEmpty() || book.isEmpty()) {
            throw new NotFoundException();
        }

        List<Book> userBooks = user.get().getFavouriteBooks();
        if (!userBooks.contains(book.get())) {
            throw new NoSuchElementException();
        }

        userBooks.remove(book.get());
    }
}