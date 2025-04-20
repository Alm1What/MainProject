package org.example.mainpriject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.mainpriject.dto.PasswordChangeDto;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.exception.AccessDeniedException;
import org.example.mainpriject.model.Role;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.RoleRepository;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }

    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestBody PasswordChangeDto passwordChangeDto) {

        User currentUser = userService.getCurrentUser();
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Неможливо змінити пароль з наданими даними");
        }

        userService.changePassword(userId, passwordChangeDto);
        return ResponseEntity.ok(Map.of("message", "Пароль успішно змінено"));
    }
}
