package com.microgram.error;

import com.microgram.exception.ForbiddenException;
import com.microgram.exception.PostNotFoundException;
import com.microgram.exception.UserNotFoundException;
import com.microgram.exception.CommentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        log.warn("Пользователь не найден: {}", ex.getMessage());
        model.addAttribute("status", 404);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePostNotFound(PostNotFoundException ex, Model model) {
        log.warn("Публикация не найдена: {}", ex.getMessage());
        model.addAttribute("status", 404);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCommentNotFound(CommentNotFoundException ex, Model model) {
        log.warn("Комментарий не найден: {}", ex.getMessage());
        model.addAttribute("status", 404);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(ForbiddenException ex, Model model) {
        log.warn("Доступ запрещён: {}", ex.getMessage());
        model.addAttribute("status", 403);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneral(Exception ex, Model model) {
        log.error("Необработанная ошибка: {}", ex.getMessage(), ex);
        model.addAttribute("status", 500);
        model.addAttribute("message", "Внутренняя ошибка сервера");
        return "errors/error";
    }
}