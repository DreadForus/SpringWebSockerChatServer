package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.MessageAction;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin()
@RestController
@RequestMapping(value="/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private String avatarUrl = "https://api.adorable.io/avatars/285/%d.png";

    @MessageMapping("/authorization.action")
    @SendToUser("/message/authorization")
    public User authorizationAction( SimpMessageHeaderAccessor headerAccessor, User user) {
        logger.info(user.toString());

        User existingUser = userRepository.findByName(user.getName());

        System.out.println(existingUser);

        if(existingUser == null){
            int randomInt = new Random().nextInt(1000) + 1;

            existingUser = new User();
            existingUser.setName(user.getName());
            existingUser.setAvatar(String.format(avatarUrl, randomInt));

            userRepository.save(existingUser);
        }

        headerAccessor.getSessionAttributes().put("user", existingUser);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAction(MessageAction.JOINED);
        chatMessage.setFrom(user);

        messagingTemplate.convertAndSend("/topic/public", chatMessage);

        return existingUser;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public User edit(@RequestBody  User user) throws Exception {
        User existingUser = userRepository.findByName(user.getName());

        System.out.println(existingUser);

        if(existingUser == null){
            throw new Exception();
        }

        existingUser.setName(user.getName());
        userRepository.save(existingUser);

        existingUser.setAction(user.getAction());

        messagingTemplate.convertAndSend("/topic/public", existingUser);

        return existingUser;
    }

}
