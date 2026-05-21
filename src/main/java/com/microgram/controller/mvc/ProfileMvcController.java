package com.microgram.controller.mvc;

import kg.attractor.microgram.dto.UserDto;
import kg.attractor.microgram.service.PostService;
import kg.attractor.microgram.service.SubscriptionService;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// MVC контроллер профилей пользователей
// Конспект Часть 3: "@Controller, @PathVariable"
// Задание: "Страница профиля, подписки"
// Ориентир: ProfileMvcController.java из JobSearch
@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileMvcController {

    private final UserService userService;
    private final PostService postService;
    private final SubscriptionService subscriptionService;

    // GET /users/{username} — страница профиля
    // Задание: "Страница профиля: Логин, Аватар, Имя, Инфо, Публикации"
    // Доступна всем — авторизованным и нет
    @GetMapping("/{username}")
    public String profile(
            @PathVariable String username,
            Authentication auth,
            Model model
    ) {
        UserDto profileUser = userService.getUserDtoByUsername(username);
        model.addAttribute("profileUser", profileUser);

        String currentEmail = auth != null ? auth.getName() : null;
        model.addAttribute("posts",
                postService.getPostsByUsername(username, currentEmail));

        if (auth != null) {
            boolean isFollowing = subscriptionService
                    .isFollowing(auth.getName(), username);
            model.addAttribute("isFollowing", isFollowing);

            boolean isOwnProfile = auth.getName()
                    .equals(profileUser.getEmail());
            model.addAttribute("isOwnProfile", isOwnProfile);
        }

        log.debug("Открыт профиль: {}", username);
        return "users/profile";
    }

    // POST /users/{username}/subscribe — подписаться / отписаться
    // Задание: "Возможность подписываться на других пользователей"
    @PostMapping("/{username}/subscribe")
    public String subscribe(
            @PathVariable String username,
            Authentication auth
    ) {
        subscriptionService.toggleSubscription(auth.getName(), username);
        return "redirect:/users/" + username;
    }
}