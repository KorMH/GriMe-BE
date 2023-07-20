package com.sparta.grimebe.global.security.filter;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
