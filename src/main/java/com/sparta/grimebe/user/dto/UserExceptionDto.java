package com.sparta.grimebe.user.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserExceptionDto {
    private final String success;
    private final int status;
    private final Map<String, String> errors;

    public UserExceptionDto(String success, int status, Map<String, String> errors) {
        this.success = success;
        this.status = status;
        this.errors = errors;
    }
}
