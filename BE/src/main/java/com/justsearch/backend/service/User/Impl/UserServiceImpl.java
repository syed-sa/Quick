package com.justsearch.backend.service.User.Impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.justsearch.backend.dto.RoleDto;
import com.justsearch.backend.dto.UserDto;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.User.UserService;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getRoles().stream()
                                .map(role -> new RoleDto(role.getName()))
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }
}