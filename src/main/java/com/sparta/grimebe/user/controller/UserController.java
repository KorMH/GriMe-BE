package com.sparta.grimebe.user.controller;

import com.sparta.grimebe.user.dto.CheckIdRequestDTO;
import com.sparta.grimebe.user.dto.SignupRequestDto;
import com.sparta.grimebe.user.service.UserService;
import com.sparta.grimebe.global.BaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;


    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "중복된 username 입니다."),
    })
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

    @Operation(summary = "아이디 중복체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용할 수 있는 아이디 입니다."),
            @ApiResponse(responseCode = "400", description = "사용이 불가능한 아이디입니다.")
    })
    //아이디 중복체크
    @PostMapping("/checkId")
    public ResponseEntity<BaseResponseDTO> checkId(@Valid @RequestBody CheckIdRequestDTO checkIdRequestDTO){
        BaseResponseDTO response = userService.checkId(checkIdRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
