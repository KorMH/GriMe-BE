package com.sparta.grimebe.post.dto;

import java.time.LocalDateTime;

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

    // private String username;

    private LocalDateTime createdAt;

//    private List<Comment> commentList;

    public PostResponseDTO(Post post) {
        this.title = post.getTitle();
        this.image = post.getImage();
        this.content = post.getContent();
        this.mood = post.getMood();
        this.weather = post.getMood();
        // this.username = post.getusername;
        this.createdAt = post.getCreatedAt();
    }
}
