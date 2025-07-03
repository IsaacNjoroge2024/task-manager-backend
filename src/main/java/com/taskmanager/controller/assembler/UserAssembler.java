package com.taskmanager.controller.assembler;

import com.taskmanager.controller.dto.user.UserResponse;
import com.taskmanager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}