package com.sparta.grimebe.User.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.grimebe.User.dto.CheckIdRequestDTO;
import com.sparta.grimebe.User.dto.SignupRequestDto;
import com.sparta.grimebe.User.service.UserService;
import com.sparta.grimebe.global.BaseResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // User Signup
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDTO> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BaseResponseDTO("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseDTO("회원가입 성공", HttpStatus.CREATED.value()));
    }

    //아이디 중복체크
    @PostMapping("/checkId")
    public ResponseEntity<BaseResponseDTO> checkId(@Valid @RequestBody CheckIdRequestDTO checkIdRequestDTO){
        BaseResponseDTO response = userService.checkId(checkIdRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
