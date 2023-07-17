package com.sparta.grimebe.User.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.grimebe.User.dto.UserExceptionDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j(topic = "AuthExceptionFilter")
@RequiredArgsConstructor
public class AuthExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("AuthExceptionFilter running!");
        try {
            filterChain.doFilter(request,response);
        } catch (JwtAuthenticationException | NullPointerException | IllegalArgumentException e){
            handleException(response, HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errors = Collections.singletonMap("error", message);
        UserExceptionDto responseBody = new UserExceptionDto("false", status.value(), errors);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}