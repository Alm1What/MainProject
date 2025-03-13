package org.example.mainpriject.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mainpriject.dto.CreateTaskDto;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.Task;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task toggleTaskCompletion(Long id) {
        Task existingTask = getTaskById(id); // Це перевірить доступ

        existingTask.setCompleted(!existingTask.isCompleted());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public Task createTask(CreateTaskDto createTaskDto) {
        User currentUser = userService.getCurrentUser();

        Task task = new Task();
        task.setTitle(createTaskDto.getTitle());
        task.setDescription(createTaskDto.getDescription());
        task.setOwner(currentUser);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public List<Task> getUserTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByOwner(currentUser);
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Завдання не знайдено"));

        User currentUser = userService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!task.getOwner().getId().equals(currentUser.getId()) &&
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Доступ заборонено");
        }

        return task;
    }

    @Transactional
    public Task updateTask(Long taskId, CreateTaskDto taskDto) {
        User user = userService.getCurrentUser();

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Завдання не знайдено"));

        if (!existingTask.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("Ви не маєте прав для оновлення цього завдання");
        }

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public List<Task> getAllTasks(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Task> taskPage = taskRepository.findAll(pageable);
            return taskPage.getContent();
        } else {
            throw new AccessDeniedException("Доступ заборонено. Потрібна роль адміністратора.");
        }
    }
}
