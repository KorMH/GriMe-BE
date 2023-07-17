package com.sparta.grimebe.global;

import com.sparta.grimebe.User.dto.UserDuplicationException;
import com.sparta.grimebe.User.exception.UserNotFoundException;
import com.sparta.grimebe.comment.exception.CommentNotFoundException;
import com.sparta.grimebe.comment.exception.NoPermissionException;
import com.sparta.grimebe.post.exception.PostNotFoundException;
import com.sparta.grimebe.post.exception.PostPermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 유저 정보 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> userNotFoundExceptionHandler(UserNotFoundException e){

        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 게시글 정보 없음
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> postNotFoundExceptionHandler(PostNotFoundException e){
        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(),404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 게시글 권한 없음
    @ExceptionHandler(PostPermissionException.class)
    public ResponseEntity<BaseResponseDTO> permissionExceptionHandler(PostPermissionException e){
        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(),403);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDTO> runtimeExceptionHandler(RuntimeException e){
        log.error(e.getMessage());
        BaseResponseDTO errorResponse = new BaseResponseDTO("알수없는 문제가 발생하였습니다.",400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    // 코멘트 정보 없음
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> commentNotFoundExceptionHandler(CommentNotFoundException e) {
        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 코멘트 권한 없음
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<BaseResponseDTO> noPermissionExceptionHandler(NoPermissionException e) {
        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(), 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 사용자 이름 중복
    @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity<BaseResponseDTO> userDuplicationExceptionHandler(UserDuplicationException e){
        BaseResponseDTO errorResponse = new BaseResponseDTO(e.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
