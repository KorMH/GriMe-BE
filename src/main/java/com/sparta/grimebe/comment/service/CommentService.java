package com.sparta.grimebe.comment.service;

import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.comment.entity.Comment;
import com.sparta.grimebe.comment.exception.CommentNotFoundException;
import com.sparta.grimebe.comment.exception.NoPermissionException;
import com.sparta.grimebe.comment.repository.CommentRepository;
import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.post.entity.Post;
import com.sparta.grimebe.post.repository.PostRepository;
import com.sparta.grimebe.User.entity.User;
import com.sparta.grimebe.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CommentResponseDto> getAllComments() {
        // 코멘트 목록을 가져와서 CommentResponseDto 객체로 변환
        return commentRepository.findAll().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto getComment(Long commentId) {
        // commentId에 해당하는 코멘트를 찾고, CommentResponseDto 객체로 변환
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new NoPermissionException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() -> new NoPermissionException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(requestDto.getContent(), post, user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    private boolean hasRole(UserDetailsImpl userDetails, Comment comment) {
        return userDetails.getUser().getId().equals(comment.getUser().getId());
    }

    @Transactional
    public BaseResponseDTO updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));
        if (!hasRole(userDetails, comment)) {
            throw new NoPermissionException("해당 작업을 수행할 권한이 없습니다.");
        }
        comment.updateContent(requestDto.getContent());
        return new BaseResponseDTO("댓글 수정 성공", 200);
    }

    public BaseResponseDTO deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));
        if (!hasRole(userDetails, comment)) {
            throw new NoPermissionException("해당 작업을 수행할 권한이 없습니다.");
        }
        commentRepository.deleteById(commentId);
        return new BaseResponseDTO("댓글 삭제 성공", 200);
    }
}
