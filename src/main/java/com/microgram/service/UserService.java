package com.microgram.service;

import com.microgram.dto.UserCreateDto;
import com.microgram.dto.UserDto;
import com.microgram.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(UserCreateDto dto);

    UserDto getUserDtoByEmail(String email);

    User getUserByEmail(String email);

    UserDto getUserDtoByUsername(String username);

    User getUserByUsername(String username);

    User getUserById(Long id);

    List<UserDto> searchUsers(String query);

    void updateProfile(String email, UserDto dto);
}