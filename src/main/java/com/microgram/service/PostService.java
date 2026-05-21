package com.microgram.service;

import com.microgram.dto.PostCreateDto;
import com.microgram.dto.PostDto;

import java.util.List;

public interface PostService {

    void createPost(PostCreateDto dto, String authorEmail);

    PostDto getPostById(Long id, String currentUserEmail);

    List<PostDto> getFeedForUser(String email);

    List<PostDto> getPostsByUsername(String username, String currentUserEmail);

    void deletePost(Long postId, String currentUserEmail);

    void toggleLike(Long postId, String currentUserEmail);
}