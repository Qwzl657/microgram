package com.microgram.controller;

import jakarta.validation.Valid;
import com.microgram.dto.CommentCreateDto;
import com.microgram.dto.PostCreateDto;
import com.microgram.service.CommentService;
import com.microgram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new PostCreateDto());
        return "posts/new";
    }

    @PostMapping
    public String createPost(
            @Valid @ModelAttribute("post") PostCreateDto dto,
            BindingResult errors,
            Authentication auth,
            Model model
    ) {
        if (errors.hasErrors()) {
            return "posts/new";
        }

        postService.createPost(dto, auth.getName());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewPost(
            @PathVariable Long id,
            Authentication auth,
            Model model
    ) {
        String currentEmail = auth != null ? auth.getName() : null;

        model.addAttribute("post",
                postService.getPostById(id, currentEmail));

        model.addAttribute("comments",
                commentService.getCommentsByPostId(id));

        model.addAttribute("newComment", new CommentCreateDto());

        return "posts/view";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            Authentication auth
    ) {
        postService.deletePost(id, auth.getName());
        return "redirect:/";
    }

    @PostMapping("/{id}/like")
    public String toggleLike(
            @PathVariable Long id,
            Authentication auth
    ) {
        postService.toggleLike(id, auth.getName());
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @Valid @ModelAttribute("newComment") CommentCreateDto dto,
            BindingResult errors,
            Authentication auth,
            Model model
    ) {
        if (errors.hasErrors()) {
            String currentEmail = auth.getName();
            model.addAttribute("post",
                    postService.getPostById(id, currentEmail));
            model.addAttribute("comments",
                    commentService.getCommentsByPostId(id));
            return "posts/view";
        }

        commentService.addComment(id, dto, auth.getName());
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Authentication auth
    ) {
        commentService.deleteComment(commentId, auth.getName());
        return "redirect:/posts/" + postId;
    }
}