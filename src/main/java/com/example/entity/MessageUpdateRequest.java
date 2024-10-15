package com.example.entity;


//DTO to read value for Message Update requests
public class MessageUpdateRequest {
    private String messageText;

    // Getters and Setters
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}