package com.sparta.grimebe.post.dto;

import com.sparta.grimebe.post.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWithLikeDTO {

    private Post post;

    private boolean isLiked;

    private Long likeCount;

    public PostWithLikeDTO(Post post, boolean isLiked, Long likeCount) {
        this.post = post;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
    }
}
