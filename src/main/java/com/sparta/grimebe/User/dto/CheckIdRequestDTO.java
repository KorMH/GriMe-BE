package com.sparta.grimebe.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CheckIdRequestDTO {

    @NotBlank(message = "아이디는 NULL, 빈칸, 공백일 수 없습니다.")                                                   // ID는 공백일 수 없으므로 Not Blank 어노테이션 사용
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="아이디를 4~10자로 입력해주세요.(특수문자x)")
    private String username;

}
