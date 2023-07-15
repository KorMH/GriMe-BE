package com.sparta.grimebe.comment.repository;

import com.sparta.grimebe.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
