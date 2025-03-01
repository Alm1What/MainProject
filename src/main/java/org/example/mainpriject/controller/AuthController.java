package org.example.mainpriject.controller;

import org.example.mainpriject.config.JwtUtil;
import org.example.mainpriject.dto.JwtResponse;
import org.example.mainpriject.dto.LoginDto;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Реєстрація користувача
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    // Логін користувача: повертає JWT токен
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginDto loginDto) {
        // Аутентифікація користувача
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерація JWT токену
        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

