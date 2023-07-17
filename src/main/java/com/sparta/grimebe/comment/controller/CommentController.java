package com.sparta.grimebe.comment.controller;

import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.comment.service.CommentService;
import com.sparta.grimebe.global.BaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @Operation(summary = "전체 댓글 조회")
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> responseDtoList = commentService.getAllComments();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
    @Operation(summary = "게시글 댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto responseDto = commentService.getComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponseDTO> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        BaseResponseDTO response = commentService.updateComment(commentId, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponseDTO> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        BaseResponseDTO response = commentService.deleteComment(commentId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
