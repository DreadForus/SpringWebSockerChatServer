package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.MessageAction;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.ParticipantRepository;
import com.example.websocketdemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@CrossOrigin()
@RestController
@RequestMapping(value="/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private String avatarUrl = "https://api.adorable.io/avatars/285/%d.png";

    @SubscribeMapping("/chat.participants")
    public Collection<User> retrieveParticipants() {
        return participantRepository.getActiveSessions().values();
    }

    @MessageMapping("/authorization.user")
    @SendToUser("/message/authorization.user")
    public User authorizationAction( SimpMessageHeaderAccessor headerAccessor, User user) {
        logger.info(user.toString());

        User existingUser = userRepository.findByUsername(user.getUsername());

        if(existingUser == null){
            int randomInt = new Random().nextInt(1000) + 1;

            existingUser = new User();
            existingUser.setUsername(user.getUsername());
            existingUser.setAvatar(String.format(avatarUrl, randomInt));
        }

        logger.info("authorization: " + existingUser);

        existingUser.setLastLogin(new Date());
        userRepository.save(existingUser);

        headerAccessor.getSessionAttributes().put("user", existingUser);

        Message message = new Message();
        message.setAction(MessageAction.JOINED);
        message.setFrom(user);

        participantRepository.add(headerAccessor.getSessionId(), user);

        messagingTemplate.convertAndSend("/topic/public", message);

        return existingUser;
    }

    @MessageMapping("/edit.user")
    @SendToUser("/message/edit.user")
    public User edit( SimpMessageHeaderAccessor headerAccessor, User user) throws Exception {
        logger.info(user.toString());

        Optional<User> existingUserOptional = userRepository.findById(user.getId());

        if(existingUserOptional.isPresent()){
            User existingUser = existingUserOptional.get();

            String previousName = existingUser.getUsername();

            logger.info(existingUser.toString());

            existingUser.setUsername(user.getUsername());

            userRepository.save(existingUser);

            headerAccessor.getSessionAttributes().replace("user", existingUser);

            existingUser.setPreviousName(previousName);

            Message message = new Message();
            message.setAction(MessageAction.RENAME);
            message.setFrom(existingUser);

            messagingTemplate.convertAndSend("/topic/public", message);

            return existingUser;
        }

        throw new Exception();
    }
}
