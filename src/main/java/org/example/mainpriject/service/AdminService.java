package org.example.mainpriject.service;

import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {


    private final UserService userService;
    private final UserRepository userRepository;

    public AdminService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public void deleteUser(Long userId) {

        User currentAdmin = userService.getCurrentUser();

        if (currentAdmin.getId().equals(userId)) {
            throw new RuntimeException("Адміністратор не може видалити себе");
        }

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        boolean isAdmin = userToDelete.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            throw new RuntimeException("Не можна видалити іншого адміністратора");
        }

        userToDelete.setDeleted(true);
        userToDelete.setDeletedAt(LocalDateTime.now());

        userRepository.save(userToDelete);
    }

    public void restoreUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeleted(false);
        user.setRestorationAt(LocalDateTime.now());
        userRepository.save(user);
    }

}
