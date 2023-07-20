package com.sparta.grimebe.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "아이디는 NULL, 빈칸, 공백일 수 없습니다.")
    private String username;
    @NotBlank(message = "비밀번호는 NULL, 빈칸, 공백일 수 없습니다.")
    private String password;
}
