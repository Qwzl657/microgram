package com.microgram.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.microgram.dto.CommentCreateDto;
import kg.attractor.microgram.dto.PostCreateDto;
import kg.attractor.microgram.service.CommentService;
import kg.attractor.microgram.service.PostService;
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

// MVC контроллер публикаций и комментариев
// Конспект Часть 3: "@Controller, @Valid, BindingResult"
// Задание: "Управление публикациями и комментариями"
// Ориентир: ResumeMvcController.java, VacancyMvcController.java из JobSearch
@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostMvcController {

    private final PostService postService;
    private final CommentService commentService;

    // GET /posts/new — форма создания поста
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new PostCreateDto());
        return "posts/new";
    }

    // POST /posts — создать пост
    // Конспект Блок 1: "@Valid — Bean Validation перед сохранением"
    @PostMapping
    public String createPost(
            @Valid @ModelAttribute("post") PostCreateDto dto,
            BindingResult errors,
            Authentication auth
    ) {
        if (errors.hasErrors()) {
            return "posts/new";
        }
        postService.createPost(dto, auth.getName());
        return "redirect:/";
    }

    // GET /posts/{id} — страница поста с комментариями
    // Задание: "Отобразить все комментарии от самого первого"
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

    // POST /posts/{id}/delete — удалить пост
    // Задание: "Пользователь может удалить только свою публикацию"
    @PostMapping("/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            Authentication auth
    ) {
        postService.deletePost(id, auth.getName());
        return "redirect:/";
    }

    // POST /posts/{id}/like — лайк / анлайк
    // Задание: "Одну и ту же публикацию можно лайкнуть только один раз"
    @PostMapping("/{id}/like")
    public String toggleLike(
            @PathVariable Long id,
            Authentication auth
    ) {
        postService.toggleLike(id, auth.getName());
        return "redirect:/posts/" + id;
    }

    // POST /posts/{id}/comments — добавить комментарий
    // Задание: "Добавление комментариев к своей или чужой публикации"
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

    // POST /posts/{postId}/comments/{commentId}/delete — удалить комментарий
    // Задание: "Можно удалить любые комментарии, но только под своей публикацией"
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