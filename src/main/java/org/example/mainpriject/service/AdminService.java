package org.example.mainpriject.service;

import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.TaskRepository;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminService {


    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public AdminService(UserService userService, UserRepository userRepository, TaskService taskService, TaskRepository taskRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
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


    @Transactional
    public void completeRemoval(Long userId) {

        // Продублював код з 30 рядка, винести як окрему функцію
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

        // Видаляємо всі завдання користувача, якщо вони є
        if (!taskRepository.findByOwner(userToDelete).isEmpty()) {
            taskRepository.deleteByOwner(userToDelete);
        }

        userRepository.delete(userToDelete);
    }

}
