package com.microgram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDto {

    private Long id;

    private UserDto user;

    private String text;

    private LocalDateTime createdAt;
}