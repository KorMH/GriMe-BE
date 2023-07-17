package com.sparta.grimebe.User.dto;

public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException(String message) {
        super(message);
    }
}