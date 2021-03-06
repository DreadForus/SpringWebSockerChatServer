package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.Message;
import com.example.websocketdemo.model.ChatAction;
import com.example.websocketdemo.model.User;
import com.example.websocketdemo.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ParticipantRepository participantRepository;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

//        User user = (User) headerAccessor.getSessionAttributes().get("user");

//        logger.info("Received a new web socket connection: " + user);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        sendUserDisconnect(event);
    }

    private void sendUserDisconnect(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        User user = (User) headerAccessor.getSessionAttributes().get("user");

        logger.info("User Disconnected : " + user);
        if(user != null) {

            Message message = new Message();
            message.setAction(ChatAction.LEFT);
            message.setFrom(user);

            messagingTemplate.convertAndSend("/topic/public", message);
        }

        participantRepository.removeParticipant(event.getSessionId());
    }


}
