package com.example.exception;

public class MessageIdNotFoundException extends RuntimeException{

    public MessageIdNotFoundException(String messString){
        super(messString);
    }
}
