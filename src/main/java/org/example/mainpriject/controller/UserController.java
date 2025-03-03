package org.example.mainpriject.controller;

import org.example.mainpriject.dto.PasswordChangeDto;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.model.User;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    // ЗАДУМКА що адмін і користувач може міняти пароль
    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestBody PasswordChangeDto passwordChangeDto) {

        User user = userService.getCurrentUser();

        // Якщо користувач – адмін, він може змінювати паролі всім
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        // Якщо це свій профіль або адмін то можна редагувати
        if (isAdmin || user.getId().equals(userId)) {
            boolean success = userService.changePassword(
                    userId,
                    passwordChangeDto.getCurrentPassword(),
                    passwordChangeDto.getNewPassword()
            );

            if (success) {
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Current password is incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Має бути описана проблема чому користувач не може цього робити але не можна писати що в нього немає дозволу бо тоді виходить так що пароль він знає"); //TODO
        }
    }


}
