package com.example.websocketdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document
@Getter
@Setter
@ToString
public class Message {

    @Id
    private String id;

    private ChatAction action;
    private String content;

    private User from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateCreate;

    public Message() {
        dateCreate = new Date();
    }
}
