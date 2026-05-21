package com.microgram.controller.mvc;

import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// MVC контроллер поиска пользователей
// Задание: "Поиск по имени, логину, email. Ссылки на профили"
// Ориентир: EmployerMvcController.java из JobSearch
@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchMvcController {

    private final UserService userService;

    // GET /search?q=... — поиск пользователей
    // Задание: "Доступен всем — авторизованным и нет"
    @GetMapping("/search")
    public String search(
            @RequestParam(name = "q", required = false, defaultValue = "") String query,
            Model model
    ) {
        model.addAttribute("query", query);
        if (!query.isBlank()) {
            model.addAttribute("results",
                    userService.searchUsers(query));
            log.debug("Поиск: '{}'", query);
        }
        return "search";
    }
}