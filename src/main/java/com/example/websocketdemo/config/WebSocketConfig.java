package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String CHAT_SEND_MESSAGE = "/chat.sendMessage";
    public static final String CHAT_PARTICIPANTS = "/chat.participants";
    public static final String TOPIC_PUBLIC = "/topic/public";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.
            addEndpoint("/ws").
            setAllowedOrigins("*").
            withSockJS().setInterceptors(new HttpSessionHandshakeInterceptor()).
            setSessionCookieNeeded(true)
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.
            setApplicationDestinationPrefixes("/app").
            setUserDestinationPrefix("/user").
            enableSimpleBroker("/topic", "/message")
        ;   // Enables a simple in-memory broker


        //   Use this for enabling a Full featured broker like RabbitMQ

        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }
}
