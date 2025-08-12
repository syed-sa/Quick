package com.justsearch.backend.service.User;

import java.util.List;

import com.justsearch.backend.dto.UserDto;

public interface UserService {
    List<UserDto> getAllUsers();
}
