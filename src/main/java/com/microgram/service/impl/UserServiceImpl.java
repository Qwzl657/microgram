package com.microgram.service.impl;

import com.microgram.dto.UserCreateDto;
import com.microgram.dto.UserDto;
import com.microgram.exception.UserNotFoundException;
import com.microgram.model.User;
import com.microgram.repository.UserRepository;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .or(() -> userRepository.findByUsername(username))
                .orElseThrow(() -> {
                    log.warn("Попытка входа: пользователь не найден: {}", username);
                    return new UsernameNotFoundException("Пользователь не найден: " + username);
                });
    }

    @Override
    @Transactional
    public void register(UserCreateDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Регистрация: email уже занят: {}", dto.getEmail());
            throw new IllegalArgumentException("Email уже используется");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Регистрация: username уже занят: {}", dto.getUsername());
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName() != null && !dto.getName().isBlank()
                        ? dto.getName()
                        : dto.getUsername())
                .avatar("default.png")
                .enabled(true)
                .build();

        userRepository.save(user);
        log.info("Зарегистрирован пользователь: {}", user.getEmail());
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        return mapToDto(getUserByEmail(email));
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new UserNotFoundException("Email не может быть пустым");
        }
        return userRepository.findByEmail(email)
                .or(() -> userRepository.findByUsername(email))
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по email/username: {}", email);
                    return new UserNotFoundException("Пользователь не найден: " + email);
                });
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return mapToDto(getUserByUsername(username));
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new UserNotFoundException("Username не может быть пустым");
        }
        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по username/email: {}", username);
                    return new UserNotFoundException("Пользователь не найден: " + username);
                });
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по id: {}", id);
                    return new UserNotFoundException("Пользователь не найден по id: " + id);
                });
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        log.debug("Поиск пользователей: '{}'", query);
        return userRepository.searchUsers(query)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateProfile(String email, UserDto dto) {
        User user = getUserByEmail(email);

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }
        if (dto.getAvatar() != null && !dto.getAvatar().isBlank()) {
            user.setAvatar(dto.getAvatar());
        }

        userRepository.save(user);
        log.info("Профиль обновлён: {}", email);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .bio(user.getBio())
                .avatar(user.getAvatar())
                .postCount(user.getPostCount() != null ? user.getPostCount() : 0)
                .followersCount(user.getFollowersCount() != null ? user.getFollowersCount() : 0)
                .followingCount(user.getFollowingCount() != null ? user.getFollowingCount() : 0)
                .build();
    }
}