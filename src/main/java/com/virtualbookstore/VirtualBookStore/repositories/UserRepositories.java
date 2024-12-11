package com.virtualbookstore.VirtualBookStore.repositories;

import com.virtualbookstore.VirtualBookStore.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepositories extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
