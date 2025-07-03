package com.taskmanager.controller;

import com.taskmanager.controller.assembler.UserAssembler;
import com.taskmanager.controller.dto.auth.LoginRequest;
import com.taskmanager.controller.dto.auth.LoginResponse;
import com.taskmanager.controller.dto.auth.RegisterRequest;
import com.taskmanager.controller.dto.user.UserResponse;
import com.taskmanager.entity.User;
import com.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserAssembler userAssembler;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        UserResponse response = userAssembler.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}