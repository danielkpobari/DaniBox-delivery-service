package com.polaris.polaris_digitech.service;

import com.polaris.polaris_digitech.dto.LoginResponse;
import com.polaris.polaris_digitech.dto.RegisterResponse;
import com.polaris.polaris_digitech.dto.UserDto;
import com.polaris.polaris_digitech.model.User;
import com.polaris.polaris_digitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public RegisterResponse register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(userDto.getUsername());
        return new RegisterResponse("User registered successfully");
    }

    public LoginResponse login(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        String token = jwtService.generateToken(userDto.getUsername());
        return new LoginResponse(token, "Login successful");
    }
}