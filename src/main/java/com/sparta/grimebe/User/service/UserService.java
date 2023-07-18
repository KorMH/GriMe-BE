package com.sparta.grimebe.User.service;

import com.sparta.grimebe.User.dto.LoginRequestDto;
import com.sparta.grimebe.User.dto.SignupRequestDto;
import com.sparta.grimebe.User.entity.User;
import com.sparta.grimebe.User.entity.UserRoleEnum;
import com.sparta.grimebe.User.exception.UserDuplicationException;
import com.sparta.grimebe.User.exception.UserNotFoundException;
import com.sparta.grimebe.User.jwt.JwtUtil;
import com.sparta.grimebe.User.repository.UserRepository;
import com.sparta.grimebe.global.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username, password, role);

        userRepository.save(user);
    }

    public BaseResponseDTO checkId(String username) {
        boolean isDuplicated = userRepository.existsByUsername(username);
        if(isDuplicated){
            throw new UserDuplicationException("사용이 불가능한 아이디 입니다.");
        }
        return new BaseResponseDTO("사용 가능한 아이디 입니다.",HttpStatus.OK.value());
    }

}
