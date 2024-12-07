package com.onlib.core.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlib.core.model.LibraryUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<LibraryUser, Long>{
    Optional<LibraryUser> findByName(String name);
}