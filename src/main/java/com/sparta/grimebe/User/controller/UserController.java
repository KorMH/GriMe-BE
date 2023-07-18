package com.sparta.grimebe.User.controller;

import com.sparta.grimebe.User.dto.LoginRequestDto;
import com.sparta.grimebe.User.dto.SignupRequestDto;
import com.sparta.grimebe.User.jwt.JwtUtil;
import com.sparta.grimebe.User.service.UserService;
import com.sparta.grimebe.global.BaseResponseDTO;
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
    public ResponseEntity<BaseResponseDTO> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BaseResponseDTO("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new BaseResponseDTO("회원가입 성공", HttpStatus.CREATED.value()));
    }
    //@PathVariable String username
    //아이디 중복체크
    @PostMapping("/checkId")
    public ResponseEntity<BaseResponseDTO> checkId(@RequestBody String username){
        BaseResponseDTO response = userService.checkId(username);
        return ResponseEntity.status(201).body(response);
    }
}
