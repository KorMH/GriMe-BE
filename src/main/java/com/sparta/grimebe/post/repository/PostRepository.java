package com.sparta.grimebe.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.grimebe.user.entity.User;
import com.sparta.grimebe.post.dto.PostListResponseDTO;
import com.sparta.grimebe.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT new com.sparta.grimebe.post.dto.PostListResponseDTO(p.id, p.title, p.image, p.user.username, count(c), count(pl),CASE WHEN COUNT(pl) > 0 THEN true ELSE false END) "
        + "FROM Post p "
        + "LEFT JOIN Comment c ON c.post = p "
        + "LEFT JOIN PostLike pl ON pl.post = p AND pl.user = :user "
        + "group by p "
        + "order by p.createdAt DESC")
    Slice<PostListResponseDTO> getPostListNEWEST(User user, Pageable pageable);

    @Query("SELECT new com.sparta.grimebe.post.dto.PostListResponseDTO(p.id, p.title, p.image, p.user.username, count(c), count(pl),CASE WHEN COUNT(pl) > 0 THEN true ELSE false END) "
        + "FROM Post p "
        + "LEFT JOIN Comment c ON c.post = p "
        + "LEFT JOIN PostLike pl ON pl.post = p AND pl.user = :user "
        + "group by p "
        + "order by count(pl) DESC")
    Slice<PostListResponseDTO> getPostListHot(User user, Pageable pageable);

    @Query("select p from Post p join fetch p.user u where p.id = :postId")
    Optional<Post> getPost(Long postId);

    // @Query("Select new com.sparta.grimebe.post.dto.PostWithLikeDTO(p, CASE WHEN COUNT(pl) > 0 THEN true ELSE false END) "
    //     + "FROM Post p "
    //     + "LEFT JOIN PostLike pl ON pl.post = p AND pl.user = :user "
    //     + "WHERE p.id = :postId "
    //     + "GROUP BY p")
    // PostWithLikeDTO getPost(Long postId, User user);
}
