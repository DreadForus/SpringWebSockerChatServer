package com.example.websocketdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String avatar;

    @Transient
    private MessageAction action;

    @Transient
    private String previousName;

    private Date lastLogin;

//    private List<Message> messages;
}
