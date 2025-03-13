package org.example.mainpriject.service;

import org.example.mainpriject.exception.AccessDeniedException;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.exception.ValidationException;
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
    private final TaskRepository taskRepository;

    public AdminService(UserService userService, UserRepository userRepository, TaskRepository taskRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    public void deleteUser(Long userId) {
        User currentAdmin = userService.getCurrentUser();

        if (currentAdmin.getId().equals(userId)) {
            throw new ValidationException("Адміністратор не може видалити себе");
        }

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));

        boolean isAdmin = userToDelete.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            throw new AccessDeniedException("Не можна видалити іншого адміністратора");
        }

        userToDelete.setDeleted(true);
        userToDelete.setDeletedAt(LocalDateTime.now());

        userRepository.save(userToDelete);
    }

    public void restoreUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));
        user.setDeleted(false);
        user.setRestorationAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void completeRemoval(Long userId) {
        User currentAdmin = userService.getCurrentUser();

        if (currentAdmin.getId().equals(userId)) {
            throw new ValidationException("Адміністратор не може видалити себе");
        }

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));

        boolean isAdmin = userToDelete.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            throw new AccessDeniedException("Не можна видалити іншого адміністратора");
        }

        userToDelete.getRoles().clear();
        userRepository.save(userToDelete);

        if (!taskRepository.findByOwner(userToDelete).isEmpty()) {
            taskRepository.deleteByOwner(userToDelete);
        }

        userRepository.delete(userToDelete);
    }

}
