package com.sparta.grimebe.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequestDto {         // 회원가입용 RequestDto

    @NotBlank(message = "아이디는 NULL, 빈칸, 공백일 수 없습니다.")                                                   // ID는 공백일 수 없으므로 Not Blank 어노테이션 사용
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="아이디를 4~10자로 입력해주세요.(특수문자x)")         // 아이디 생성 규칙 관련 정규식 선언 (4~10자 사이, 소문자, 숫자만 사용 가능)
    private String username;

    @NotBlank(message = "비밀번호는 NULL, 빈칸, 공백일 수 없습니다.")                                                                                  // 비밀번호도 공백일 수 없으므로 Not Blank 어노테이션 사용
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message="비밀번호를 8~15자로 입력해주세요.(특수문자o)")    // 비밀번호 생성 규칙 관련 정규식(8~15자 사이,대/소문자, 숫자, 특수문자)
    private String password;
}
