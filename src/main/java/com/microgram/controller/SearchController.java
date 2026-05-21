package com.microgram.controller;

import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchController {

    private final UserService userService;

    @GetMapping("/search")
    public String search(
            @RequestParam(name = "q", required = false, defaultValue = "") String query,
            Model model
    ) {
        model.addAttribute("query", query);

        if (!query.isBlank()) {
            model.addAttribute("results",
                    userService.searchUsers(query));
            log.debug("Поиск по запросу '{}' выполнен", query);
        }

        return "search";
    }
}