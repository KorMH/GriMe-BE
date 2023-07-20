package com.sparta.grimebe.postlike.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.postlike.service.PostLikeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}")
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 게시글 좋아요 메서드
    @Operation(summary = "좋아요")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "좋아요 성공/삭제"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})
    @PostMapping("/likes")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.likePost(postId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
