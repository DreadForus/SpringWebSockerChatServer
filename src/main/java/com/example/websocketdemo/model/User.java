package com.example.websocketdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user")
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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MessageAction getAction() {
        return action;
    }
    public void setAction(MessageAction action) {
        this.action = action;
    }

    public String getPreviousName() {
        return previousName;
    }
    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
