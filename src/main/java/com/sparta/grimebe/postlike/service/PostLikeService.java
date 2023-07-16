package com.sparta.grimebe.postlike.service;

import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.post.entity.Post;
import com.sparta.grimebe.post.service.PostService;
import com.sparta.grimebe.postlike.entity.PostLike;
import com.sparta.grimebe.postlike.repository.PostLikeRepository;
import com.sparta.grimebe.User.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository, PostService postService){
        this.postLikeRepository = postLikeRepository;
        this.postService = postService;
    }

    // likePost 메서드를 여기로 이동
    @Transactional
    public void likePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postService.getPostById(postId);
        User user = userDetails.getUser();
        Optional<PostLike> optionalPostLike = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId());

        if (optionalPostLike.isPresent()) {
            postLikeRepository.deleteByUserIdAndPostId(user.getId(), post.getId());
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }
}
