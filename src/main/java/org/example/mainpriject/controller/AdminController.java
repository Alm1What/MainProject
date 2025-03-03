package org.example.mainpriject.controller;

import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.AdminService;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Встановлення ролі адміністратора для користувача
    @PostMapping("/set-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> setAdminRole(@RequestParam String email) {
        userService.setAdminRole(email);
        return ResponseEntity.ok("Admin role assigned to " + email);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }


    // це не повне видалення користувача а лише зміна deleted на true (хочу спробувати реалізувати функціонал для цього "Мяке видалення").
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }

    @PatchMapping("/users/{id}/restore")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> restoreUser(@PathVariable Long id) {
        adminService.restoreUser(id);
        return ResponseEntity.ok().build();
    }

    // Повне видалення користувача
    @DeleteMapping("/total-remove/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void totalRemoveUser(@PathVariable Long id) {
        adminService.completeRemoval(id);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserDto updatedUserDto) {
        UserDto updatedUser = userService.updateUserProfile(userId, updatedUserDto);
        return ResponseEntity.ok(updatedUser);
    }

}
