package com.sparta.grimebe.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.grimebe.post.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
