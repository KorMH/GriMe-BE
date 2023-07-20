package com.sparta.grimebe.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class PostListResponseDTO {

    private Long id;

    private String title;

    private String image;

    private String username;

    private Long commentCount;

    private Long likeCount;

    private Boolean isLiked;

    public PostListResponseDTO(Long id, String title, String image, String username, Long commentCount, Long likeCount,
        Boolean isLiked) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.username = username;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
