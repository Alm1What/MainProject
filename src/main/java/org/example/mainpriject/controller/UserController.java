package org.example.mainpriject.controller;

import org.example.mainpriject.dto.PasswordChangeDto;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.exception.AccessDeniedException;
import org.example.mainpriject.model.User;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }

    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestBody PasswordChangeDto passwordChangeDto) {

        User user = userService.getCurrentUser();
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin || user.getId().equals(userId)) {
            userService.changePassword(userId, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
            return ResponseEntity.ok().body(Map.of("message", "Password changed successfully"));
        } else {
            throw new AccessDeniedException("Неможливо змінити пароль з наданими даними");
        }
    }
}
