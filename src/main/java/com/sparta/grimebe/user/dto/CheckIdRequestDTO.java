package com.sparta.grimebe.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CheckIdRequestDTO {

    @NotBlank(message = "아이디는 NULL, 빈칸, 공백일 수 없습니다.")
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="아이디를 4~10자로 입력해주세요.(특수문자x)")
    private String username;

}
