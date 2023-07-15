package com.sparta.grimebe.comment.dto;

import com.sparta.grimebe.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userName;
    private String postTitle;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUsername();
        this.postTitle = comment.getPost().getTitle();
    }

}
