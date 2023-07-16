package com.sparta.grimebe.postlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.grimebe.postlike.entity.PostLike;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPostId(Long postId);
    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
