package com.sparta.grimebe.post.controller;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.grimebe.global.security.UserDetailsImpl;
import com.sparta.grimebe.global.BaseResponseDTO;
import com.sparta.grimebe.post.dto.PagingDTO;
import com.sparta.grimebe.post.dto.PagingParam;
import com.sparta.grimebe.post.dto.PostListResponseDTO;
import com.sparta.grimebe.post.dto.PostRequestDTO;
import com.sparta.grimebe.post.dto.PostResponseDTO;
import com.sparta.grimebe.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시글", description = "게시글 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 리스트 조회")
    @ApiResponses(value =
        { @ApiResponse(responseCode = "200", description = "게시글 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    @GetMapping("/post")
    public ResponseEntity<PagingDTO<List<PostListResponseDTO>>> getPosts(
        @Parameter(description = "페이지 네이션 QueryParameter입니다. 모두 생략 가능하며 sort 생략했을 시 최신순으로 조회됩니다. 인기순 조회 시 sort=HOT 입니다.")
        @ModelAttribute PagingParam pagingParam,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Slice<PostListResponseDTO> result = postService.getPosts(pagingParam, userDetails);
        PagingDTO<List<PostListResponseDTO>> response = new PagingDTO<>(result.hasNext(),result.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 상세 조회")
    @ApiResponses(value =
        { @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDTO response = postService.getPost(postId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 작성")
    @ApiResponses(value =
        { @ApiResponse(responseCode = "201", description = "게시글 작성 성공, 게시글들의 내용과 S3에 저장된 이미지 파일 경로를 json 형식으로 반환합니다."),
            @ApiResponse(responseCode = "400", description = "이미지 파일 관련 문제"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")})
    @PostMapping(value = "/post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PostResponseDTO> createPost(
        @Parameter(description = "이미지를 제외한 게시글 내용들을 받습니다. json 형식으로 받으며 Key 값은 request 입니다.") @RequestPart(value = "request") PostRequestDTO postRequestDTO,
        @Parameter(description = "이미지 파일을 받습니다. 크기는 10MB 미만입니다. Key 값은 image 입니다.") @RequestPart MultipartFile image,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDTO response = postService.createPost(postRequestDTO,image, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "게시글 수정")
    @ApiResponses(value =
        { @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        ,@ApiResponse(responseCode = "403", description = "수정할 권한이 없음")
        ,@ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음")})
    @PutMapping("/post/{postId}")
    public ResponseEntity<BaseResponseDTO> modifyPost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BaseResponseDTO response = postService.modifyPost(postId, postRequestDTO,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponses(value =
        { @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "수정할 권한이 없음"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음")})
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<BaseResponseDTO> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BaseResponseDTO response = postService.deletePost(postId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
