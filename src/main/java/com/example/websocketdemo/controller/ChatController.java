package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value="/chat")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.getUsers")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public User addUser(User user, SimpMessageHeaderAccessor headerAccessor) {

        System.out.println(user);

        User existingUser = userRepository.findByUsername(user.getUsername());

        System.out.println(user);

        headerAccessor.getSessionAttributes().put("user", existingUser);
        return existingUser;
    }
}
