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

        User user = userRepository.findByEmail(username)
                .orElseGet(() ->
                        userRepository.findByUsername(username)
                                .orElseThrow(() -> {
                                    log.warn("Попытка входа с несуществующим логином: {}", username);
                                    return new UsernameNotFoundException(
                                            "Пользователь не найден: " + username
                                    );
                                })
                );

        log.info("Загружен пользователь для аутентификации: {}", user.getEmail());
        return user;
    }

    @Override
    @Transactional
    public void register(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Попытка регистрации с уже существующим email: {}", dto.getEmail());
            throw new IllegalArgumentException("Email уже используется");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Попытка регистрации с уже существующим username: {}", dto.getUsername());
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName() != null ? dto.getName() : dto.getUsername())
                .enabled(true)
                .postCount(0)
                .followersCount(0)
                .followingCount(0)
                .build();

        userRepository.save(user);

        log.info("Зарегистрирован новый пользователь: {}", user.getEmail());
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user = getUserByEmail(email);
        return mapToDto(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по email: {}", email);
                    return new UserNotFoundException();
                });
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по username: {}", username);
                    return new UserNotFoundException();
                });
        return mapToDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден по id: {}", id);
                    return new UserNotFoundException();
                });
    }

    @Override
    public List<UserDto> searchUsers(String query) {

        log.debug("Поиск пользователей по запросу: {}", query);

        return userRepository.searchUsers(query)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateProfile(String email, UserDto dto) {
        User user = getUserByEmail(email);

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getBio() != null) user.setBio(dto.getBio());
        if (dto.getAvatar() != null) user.setAvatar(dto.getAvatar());

        userRepository.save(user);
        log.info("Профиль обновлён для пользователя: {}", email);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .bio(user.getBio())
                .avatar(user.getAvatar())
                .postCount(user.getPostCount())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .build();
    }
}