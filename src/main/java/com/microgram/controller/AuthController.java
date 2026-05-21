package com.microgram.controller;

import jakarta.validation.Valid;
import com.microgram.dto.UserCreateDto;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserCreateDto dto,
            BindingResult errors,
            Model model
    ) {

        if (errors.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.register(dto);
            log.info("Новый пользователь зарегистрирован: {}", dto.getEmail());
            return "redirect:/auth/login?registered=true";
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка регистрации: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}