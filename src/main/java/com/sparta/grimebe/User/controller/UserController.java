package com.sparta.grimebe.User.controller;

import com.sparta.grimebe.User.dto.LoginRequestDto;
import com.sparta.grimebe.User.dto.ResponseDto;
import com.sparta.grimebe.User.dto.SignupRequestDto;
import com.sparta.grimebe.User.jwt.JwtUtil;
import com.sparta.grimebe.User.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // User Signup
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto) {

        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new ResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody SignupRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto, response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDto("회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }
}
