package com.microgram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String name;

    private String bio;

    private String avatar;

    private Integer postCount;
    private Integer followersCount;
    private Integer followingCount;
}