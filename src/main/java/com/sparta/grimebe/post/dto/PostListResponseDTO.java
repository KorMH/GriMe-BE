package com.sparta.grimebe.post.dto;

import lombok.Getter;

@Getter
public class PostListResponseDTO {
    private Long id;

    private String title;

    private String image;

    private String username;

    private int commentCount;

    private int likeCount;

    public PostListResponseDTO(Long id, String title, String image, String username, int commentCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.username = username;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
}
