package com.sparta.grimebe.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.grimebe.user.entity.User;
import com.sparta.grimebe.user.entity.UserRoleEnum;
import com.sparta.grimebe.user.exception.UserNotFoundException;
import com.sparta.grimebe.user.repository.UserRepository;
import com.sparta.grimebe.global.security.UserDetailsImpl;
import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.comment.entity.Comment;
import com.sparta.grimebe.comment.exception.CommentNotFoundException;
import com.sparta.grimebe.comment.exception.NoPermissionException;
import com.sparta.grimebe.comment.repository.CommentRepository;
import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.post.entity.Post;
import com.sparta.grimebe.post.exception.PostNotFoundException;
import com.sparta.grimebe.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto,
        UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId())
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Post post = getPostById(postId);

        Comment comment = Comment.builder()
            .content(commentRequestDto.getContent())
            .post(post)
            .user(user)
            .build();

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public BaseResponseDTO updateComment(Long postId, Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        getPostById(postId);
        Comment comment = getCommentById(commentId);

        if (!hasRole(userDetails, comment)) {
            throw new NoPermissionException("작성자만 삭제/수정할 수 있습니다.");
        }

        comment.updateContent(requestDto.getContent());
        return new BaseResponseDTO("댓글 수정 성공", 200);
    }

    @Transactional
    public BaseResponseDTO deleteComment(Long postId, Long commentId, UserDetailsImpl userDetails) {
        getPostById(postId);
        Comment comment = getCommentById(commentId);

        if (!hasRole(userDetails, comment)) {
            throw new NoPermissionException("작성자만 삭제/수정할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return new BaseResponseDTO("댓글 삭제 성공", 200);
    }

    private boolean hasRole(UserDetailsImpl userDetails, Comment comment) {
        return userDetails.getAuthorities().contains(UserRoleEnum.ADMIN) ||
            comment.getUser().getUsername().equals(userDetails.getUsername());
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }
}
