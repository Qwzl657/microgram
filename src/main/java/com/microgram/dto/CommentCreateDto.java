package com.microgram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {

    @NotBlank(message = "{validation.comment.notblank}")
    @Size(max = 1000, message = "{validation.comment.size}")
    private String text;
}