package com.sparta.grimebe.User.service;

import com.sparta.grimebe.User.dto.SignupRequestDto;
import com.sparta.grimebe.User.entity.UserRoleEnum;
import com.sparta.grimebe.User.jwt.JwtUtil;
import com.sparta.grimebe.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import com.sparta.grimebe.User.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
