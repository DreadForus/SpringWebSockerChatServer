package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}