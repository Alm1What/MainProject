package org.example.mainpriject.controller;

import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    // Отримання поточного користувача (автентифікованого)
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto currentUser = userService.getCurrentUserDto();
        return ResponseEntity.ok(currentUser);
    }




}
