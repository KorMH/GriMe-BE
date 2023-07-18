package com.sparta.grimebe.comment.dto;

import com.sparta.grimebe.comment.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userName;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUsername();
    }
}
