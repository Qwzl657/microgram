package com.microgram.controller;

import com.microgram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Authentication auth, Model model) {

        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            model.addAttribute("posts",
                    postService.getFeedForUser(email));
            log.debug("Загружена лента для: {}", email);
        }

        return "index";
    }
}