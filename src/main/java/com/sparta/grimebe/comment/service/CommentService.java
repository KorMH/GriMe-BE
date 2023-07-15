package com.sparta.grimebe.comment.service;

import com.sparta.grimebe.comment.dto.CommentRequestDto;
import com.sparta.grimebe.comment.dto.CommentResponseDto;
import com.sparta.grimebe.comment.entity.Comment;
import com.sparta.grimebe.comment.exception.CommentNotFoundException;
import com.sparta.grimebe.comment.exception.NoPermissionException;
import com.sparta.grimebe.comment.repository.CommentRepository;
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
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, String token) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new NoPermissionException("권한이 없습니다."));
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() -> new NoPermissionException("권한이 없습니다."));
        Comment comment = new Comment(requestDto.getContent(), post, user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    // (이하 코드는 중복되어 생략)

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, String token) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));
        comment.updateContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String token) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));
        commentRepository.deleteById(commentId);
    }
}
