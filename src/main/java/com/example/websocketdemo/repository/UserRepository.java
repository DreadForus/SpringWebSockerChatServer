package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findOptionalByUsername(String username);
    public User findByUsername(String username);
    public Optional<User> findByUsernameAndPassword(String username, String password);

}