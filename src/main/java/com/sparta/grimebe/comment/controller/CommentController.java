package com.sparta.grimebe.comment.controller;

import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.createComment(requestDto, userDetails), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId, requestDto, userDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.deleteComment(commentId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
