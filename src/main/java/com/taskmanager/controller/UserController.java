package com.taskmanager.controller;

import com.taskmanager.controller.assembler.UserAssembler;
import com.taskmanager.controller.dto.user.UserResponse;
import com.taskmanager.entity.User;
import com.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponse> response = users.stream()
                .map(userAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse response = userAssembler.toResponse(user);
        return ResponseEntity.ok(response);
    }
}