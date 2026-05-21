package com.microgram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDto {

    private Long id;

    private UserDto user;

    private String image;

    private String description;

    private Integer likeCount;

    private Integer commentCount;

    private LocalDateTime createdAt;

    private boolean likedByCurrentUser;
}