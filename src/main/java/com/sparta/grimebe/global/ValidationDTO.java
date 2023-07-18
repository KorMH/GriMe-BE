package com.sparta.grimebe.global;

import java.util.Map;

import lombok.Getter;

@Getter
public class ValidationDTO {
    private final String msg;
    private final int status;
    private final Map<String, String> errors;

    public ValidationDTO(String msg, int status, Map<String, String> errors) {
        this.msg = msg;
        this.status = status;
        this.errors = errors;
    }
}
