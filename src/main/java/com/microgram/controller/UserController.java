package com.microgram.controller;

import com.microgram.dto.UserDto;
import com.microgram.service.PostService;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/profile")
    public String showMyProfile(Principal principal, Model model) {
        if (principal == null) {
            log.warn("Анонимный пользователь пытается открыть /profile, редирект на login");
            return "redirect:/login";
        }

        String email = principal.getName();
        log.info("Загрузка личного профиля для email: {}", email);

        UserDto currentUserDto = userService.getUserDtoByEmail(email);

        model.addAttribute("profileUser", currentUserDto);
        model.addAttribute("isOwnProfile", true);
        model.addAttribute("isFollowing", false);

        model.addAttribute("posts", postService.getPostsByUsername(currentUserDto.getUsername(), email));

        return "profile";
    }

    @GetMapping("/user/{username}")
    public String showUserProfile(@PathVariable String username, Principal principal, Model model) {
        log.info("Загрузка публичного профиля пользователя: {}", username);

        UserDto profileUserDto = userService.getUserDtoByUsername(username);
        model.addAttribute("profileUser", profileUserDto);

        String currentUserEmail = (principal != null) ? principal.getName() : null;
        boolean isOwn = false;

        if (currentUserEmail != null) {
            UserDto currentUserDto = userService.getUserDtoByEmail(currentUserEmail);
            if (currentUserDto.getUsername().equalsIgnoreCase(username)) {
                isOwn = true;
            }
        }

        model.addAttribute("isOwnProfile", isOwn);

        model.addAttribute("isFollowing", false);

        model.addAttribute("posts", postService.getPostsByUsername(username, currentUserEmail));

        return "profile";
    }
}