package com.polaris.polaris_digitech.controller;

import com.polaris.polaris_digitech.dto.LoginResponse;
import com.polaris.polaris_digitech.dto.RegisterResponse;
import com.polaris.polaris_digitech.dto.UserDto;
import com.polaris.polaris_digitech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserDto userDto) {
        RegisterResponse result = userService.register(userDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto userDto) {
        LoginResponse result = userService.login(userDto);
        return ResponseEntity.ok(result);
    }
}