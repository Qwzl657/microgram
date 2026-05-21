package com.microgram.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.microgram.dto.UserCreateDto;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// MVC контроллер авторизации и регистрации
// Конспект Часть 3: "@Controller, Model"
// Конспект Блок 1: "@Valid, BindingResult"
// Ориентир: AuthMvcController.java из JobSearch
@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthMvcController {

    private final UserService userService;

    // GET /auth/login — страница входа
    // Задание: "Авторизация доступна всем пользователям"
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // GET /auth/register — страница регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "auth/register";
    }

    // POST /auth/register — обработка регистрации
    // Конспект Блок 1: "@Valid — активирует Bean Validation"
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
            log.info("Зарегистрирован пользователь: {}", dto.getEmail());
            return "redirect:/auth/login?registered=true";
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка регистрации: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}