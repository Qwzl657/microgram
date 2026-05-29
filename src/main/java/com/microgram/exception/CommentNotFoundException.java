package com.microgram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException() {
        super("Комментарий не найден");
    }
}