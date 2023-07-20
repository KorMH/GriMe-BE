package com.sparta.grimebe.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.grimebe.global.security.UserDetailsImpl;
import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.comment.service.CommentService;
import com.sparta.grimebe.global.BaseResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/{postId}")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping("/comments")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BaseResponseDTO response = commentService.updateComment(postId, commentId, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDTO> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BaseResponseDTO response = commentService.deleteComment(postId, commentId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
