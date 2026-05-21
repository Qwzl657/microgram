package com.microgram.config;

import com.microgram.dto.UserDto;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final UserService userService;

    @ModelAttribute("currentUser")
    public UserDto getCurrentUser(Authentication auth) {

        if (auth == null || !auth.isAuthenticated()
                || auth.getName().equals("anonymousUser")) {
            return null;
        }

        try {

            return userService.getUserDtoByEmail(auth.getName());
        } catch (Exception e) {

            log.debug("Не удалось загрузить currentUser: {}", e.getMessage());
            return null;
        }
    }
}