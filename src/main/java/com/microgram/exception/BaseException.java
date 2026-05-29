package com.microgram.exception;

// Базовый класс для всех кастомных исключений проекта
// Конспект Блок 2: "Механизмы обработки: @ResponseStatus — возвращает HTTP-код ответа"
// Ориентир: BaseException.java из java_27_movie_review и JobSearch
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }
}