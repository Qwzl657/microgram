package com.microgram.controller;

import com.microgram.dto.PostDto;
import com.microgram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFeed(Authentication auth) {
        log.debug("API: запрос ленты для {}", auth.getName());
        return ResponseEntity.ok(
                postService.getFeedForUser(auth.getName())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Long id,
            Authentication auth
    ) {
        String email = auth != null ? auth.getName() : null;
        return ResponseEntity.ok(
                postService.getPostById(id, email)
        );
    }
}