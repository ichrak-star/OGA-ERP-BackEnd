package com.onegateafrica.dto;

public class Message {


    private String MessageContent;

    public Message(String messageContent) {
        this.MessageContent = messageContent;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }
}