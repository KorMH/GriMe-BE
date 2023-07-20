package com.sparta.grimebe.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.post.entity.Post;

import lombok.Getter;

@Getter
public class PostResponseDTO {

    private Long id;

    private String title;

    private String image;

    private String content;

    private String mood;

    private String weather;

    private String username;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> commentList;

    private boolean isLiked;

    public PostResponseDTO(Post post, boolean isLiked) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.image = post.getImage();
        this.content = post.getContent();
        this.mood = post.getMood();
        this.weather = post.getWeather();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
        this.commentList = post.getCommentList().stream()
            .map(CommentResponseDto::new)
            .collect(Collectors.toList());
        this.isLiked = isLiked;
    }
}
