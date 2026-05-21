package com.microgram.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostCreateDto {

    @NotNull(message = "{validation.post.image.notnull}")
    private MultipartFile image;

    @Size(max = 2200, message = "{validation.post.description.size}")
    private String description;
}