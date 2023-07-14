package com.sparta.grimebe.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.grimebe.post.dto.PostRequestDTO;
import com.sparta.grimebe.post.dto.PostResponseDTO;
import com.sparta.grimebe.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseEntity<PostResponseDTO> getPosts() {
        postService.getPosts();
        return null;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {
        postService.getPost(postId);
        return null;
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        postService.createPost(postRequestDTO);
        return null;
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO) {
        postService.modifyPost(postId,postRequestDTO);
        return null;
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return null;
    }

}
