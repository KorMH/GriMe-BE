package com.sparta.grimebe.comment.repository;

import java.util.List;

import com.sparta.grimebe.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.post.id = :postId order by c.id desc")
    List<Comment> getComments(Long postId);
}
