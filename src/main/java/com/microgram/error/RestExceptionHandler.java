package com.microgram.error;

import com.microgram.controller.PostApiController;
import com.microgram.controller.UserApiController;
import com.microgram.exception.CommentNotFoundException;
import com.microgram.exception.ForbiddenException;
import com.microgram.exception.PostNotFoundException;
import com.microgram.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {
        PostApiController.class,
        UserApiController.class
})
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseBody handleUserNotFound(UserNotFoundException ex) {
        log.warn("REST: Пользователь не найден: {}", ex.getMessage());
        return ErrorResponseBody.builder()
                .status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseBody handlePostNotFound(PostNotFoundException ex) {
        log.warn("REST: Публикация не найдена: {}", ex.getMessage());
        return ErrorResponseBody.builder()
                .status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseBody handleCommentNotFound(
            CommentNotFoundException ex) {
        log.warn("REST: Комментарий не найден: {}", ex.getMessage());
        return ErrorResponseBody.builder()
                .status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseBody handleForbidden(ForbiddenException ex) {
        log.warn("REST: Доступ запрещён: {}", ex.getMessage());
        return ErrorResponseBody.builder()
                .status(403)
                .error("Forbidden")
                .message(ex.getMessage())
                .build();
    }
}