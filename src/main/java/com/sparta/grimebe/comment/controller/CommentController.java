package com.sparta.grimebe.comment.controller;

import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestHeader("Authorization") String token, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.createComment(requestDto, token), HttpStatus.CREATED);
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
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId, requestDto, token), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId) {
        commentService.deleteComment(commentId, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
