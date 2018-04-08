package com.example.websocketdemo.model;

public class ChatMessage {
    private MessageAction action;
    private String content;
    private User from;

    public MessageAction getAction() {
        return action;
    }
    public void setAction(MessageAction action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public User getFrom() {
        return from;
    }
    public void setFrom(User from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "action=" + action +
                ", content='" + content + '\'' +
                ", from=" + from +
                '}';
    }
}
