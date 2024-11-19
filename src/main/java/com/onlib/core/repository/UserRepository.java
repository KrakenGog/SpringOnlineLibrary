package com.onlib.core.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlib.core.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
}