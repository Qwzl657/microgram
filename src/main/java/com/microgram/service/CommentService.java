package com.microgram.service;

import com.microgram.dto.CommentCreateDto;
import com.microgram.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsByPostId(Long postId);

    void addComment(Long postId, CommentCreateDto dto, String authorEmail);

    void deleteComment(Long commentId, String currentUserEmail);
}