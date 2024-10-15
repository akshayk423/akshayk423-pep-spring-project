package com.example.exception;

public class FailedMessageException extends RuntimeException{
    public FailedMessageException(String messString){
        super(messString);
    }
}
