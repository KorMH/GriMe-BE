package com.sparta.grimebe.post.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.grimebe.User.entity.User;
import com.sparta.grimebe.User.entity.UserRoleEnum;
import com.sparta.grimebe.User.exception.UserNotFoundException;
import com.sparta.grimebe.User.repository.UserRepository;
import com.sparta.grimebe.User.security.UserDetailsImpl;
import com.sparta.grimebe.comment.entity.Comment;
import com.sparta.grimebe.comment.repository.CommentRepository;
import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.post.dto.PagingParam;
import com.sparta.grimebe.post.dto.PostListResponseDTO;
import com.sparta.grimebe.post.dto.PostRequestDTO;
import com.sparta.grimebe.post.dto.PostResponseDTO;
import com.sparta.grimebe.post.entity.Post;
import com.sparta.grimebe.post.exception.PostPermissionException;
import com.sparta.grimebe.post.exception.PostNotFoundException;
import com.sparta.grimebe.post.image.ImageStore;
import com.sparta.grimebe.post.repository.PostRepository;
import com.sparta.grimebe.postlike.repository.PostLikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ImageStore imageStore;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public PostResponseDTO getPost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.getPost(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
        boolean isLiked = postLikeRepository.existsByPostIdAndUserId(postId, userDetails.getUser().getId());
        List<Comment> comments = commentRepository.getComments(post.getId());
        post.setCommentList(comments);
        PostResponseDTO response = new PostResponseDTO(post, isLiked);
        return response;
    }

    @Transactional(readOnly = true)
    public Slice<PostListResponseDTO> getPosts(PagingParam pagingParam, UserDetailsImpl userDetails) {
        log.info("paging Param = {}, {}, {}", pagingParam.getPage(), pagingParam.getSize(), pagingParam.getSort());
        Pageable pageable = PageRequest.of(pagingParam.getPage(), pagingParam.getSize());

        Slice<PostListResponseDTO> postsList = null;
        if (pagingParam.getSort().equals("HOT")){
            postsList = postRepository.getPostListHot(userDetails.getUser(),pageable);
        } else {
            postsList = postRepository.getPostListNEWEST(userDetails.getUser(), pageable);
        }
        return postsList;
    }

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, MultipartFile image, UserDetailsImpl userDetails) {
        String filePath = imageStore.storeFile(image);
        log.info("filePath = {}", filePath);
        User user = getUserById(userDetails.getUser().getId());
        Post post = Post.builder()
            .title(postRequestDTO.getTitle())
            .content(postRequestDTO.getContent())
            .user(user)
            .image(filePath)
            .mood(postRequestDTO.getMood())
            .weather(postRequestDTO.getWeather())
            .build();
        Post savedPost = postRepository.save(post);
        PostResponseDTO response = new PostResponseDTO(savedPost, false);
        return response;
    }

    @Transactional
    public BaseResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, UserDetailsImpl userDetails) {
        Post post = getPostById(postId);

        if (!hasRole(userDetails, post)){
            throw new PostPermissionException("작성자만 삭제/수정할 수 있습니다.");
        }

        post.modifyPost(postRequestDTO);
        BaseResponseDTO response = new BaseResponseDTO("게시글 수정 성공", 200);
        return response;
    }

    @Transactional
    public BaseResponseDTO deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = getPostById(postId);

        if (!hasRole(userDetails, post)){
            throw new PostPermissionException("작성자만 삭제/수정할 수 있습니다.");
        }

        postRepository.delete(post);
        BaseResponseDTO response = new BaseResponseDTO("게시글 삭제 성공", 200);
        return response;
    }

    private boolean hasRole(UserDetailsImpl userDetails, Post post) {
        return userDetails.getAuthorities().contains(UserRoleEnum.ADMIN) ||
            post.getUser().getUsername().equals(userDetails.getUsername());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }
}
