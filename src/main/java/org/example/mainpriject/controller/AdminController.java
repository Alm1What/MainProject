package org.example.mainpriject.controller;

import org.example.mainpriject.model.Role;
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


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }

    @PatchMapping("/users/{id}/restore")
    public ResponseEntity<User> restoreUser(@PathVariable Long id) {
        adminService.restoreUser(id);
        return ResponseEntity.ok().build();
    }

}
