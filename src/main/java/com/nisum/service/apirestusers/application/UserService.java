package com.nisum.service.apirestusers.application;

import com.nisum.service.apirestusers.domain.dto.user.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto showUser(UUID userId);
    UserDto updateUser(UUID userId, UserDto userDto);
    void deleteUser(UUID userId);
}
