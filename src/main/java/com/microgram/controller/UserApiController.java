package com.microgram.controller;

import com.microgram.dto.UserDto;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> search(
            @RequestParam(defaultValue = "") String q
    ) {
        log.debug("API: поиск пользователей '{}'", q);
        return ResponseEntity.ok(userService.searchUsers(q));
    }
}