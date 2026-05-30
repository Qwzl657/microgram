package com.microgram.controller.mvc;

import com.microgram.dto.UserDto;
import com.microgram.service.PostService;
import com.microgram.service.SubscriptionService;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileMvcController {

    private final UserService userService;
    private final PostService postService;
    private final SubscriptionService subscriptionService;

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
                    .isFollowing(currentEmail, username);
            model.addAttribute("isFollowing", isFollowing);

            boolean isOwnProfile = currentEmail.equals(profileUser.getEmail());
            model.addAttribute("isOwnProfile", isOwnProfile);
        } else {
            model.addAttribute("isFollowing", false);
            model.addAttribute("isOwnProfile", false);
        }

        log.debug("Открыт профиль: {}", username);
        return "users/profile";
    }

    @PostMapping("/{username}/subscribe")
    public String subscribe(
            @PathVariable String username,
            Authentication auth
    ) {
        subscriptionService.toggleSubscription(auth.getName(), username);
        return "redirect:/users/" + username;
    }
}