package com.sparta.grimebe.User.filter;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}