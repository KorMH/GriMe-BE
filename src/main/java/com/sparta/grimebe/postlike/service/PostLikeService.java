package com.sparta.grimebe.postlike.service;

import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.global.security.UserDetailsImpl;
import com.sparta.grimebe.post.entity.Post;
import com.sparta.grimebe.post.service.PostService;
import com.sparta.grimebe.postlike.entity.PostLike;
import com.sparta.grimebe.postlike.repository.PostLikeRepository;
import com.sparta.grimebe.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Transactional
    public BaseResponseDTO likePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postService.getPostById(postId);
        User user = userDetails.getUser();
        Optional<PostLike> optionalPostLike = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId());

        if (optionalPostLike.isPresent()) {
            postLikeRepository.deleteByUserIdAndPostId(user.getId(), post.getId());
            return new BaseResponseDTO("좋아요 삭제 성공", HttpStatus.OK.value());
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
            return new BaseResponseDTO("좋아요 추가 성공", HttpStatus.OK.value());
        }
    }
}
