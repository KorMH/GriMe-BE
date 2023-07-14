package com.sparta.grimebe.post.service;

import org.springframework.stereotype.Service;

import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.post.dto.PostRequestDTO;
import com.sparta.grimebe.post.dto.PostResponseDTO;
import com.sparta.grimebe.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDTO getPost(Long postId) {

        return null;
    }

    public PostResponseDTO getPosts() {

        return null;
    }

    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {

        return null;
    }

    public BaseResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO) {

        return null;
    }

    public BaseResponseDTO deletePost(Long postId) {
        return null;
    }
}
