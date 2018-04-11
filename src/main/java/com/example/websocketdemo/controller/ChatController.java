package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.websocketdemo.config.WebSocketConfig.CHAT_SEND_MESSAGE;
import static com.example.websocketdemo.config.WebSocketConfig.TOPIC_PUBLIC;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(value="/chat")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @MessageMapping(CHAT_SEND_MESSAGE)
    @SendTo(TOPIC_PUBLIC)
    public Message sendMessage(@Payload Message message) {

        return message;
    }
}
