package org.example.mainpriject.service;

import org.example.mainpriject.dto.CreateTaskDto;
import org.example.mainpriject.model.Task;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

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
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User currentUser = userService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Перевіряємо, чи поточний користувач є власником завдання або адміністратором
        if (task.getOwner().getId().equals(currentUser.getId()) ||
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return task;
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    public Task updateTask(Long id, CreateTaskDto taskDto) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    // Зміна статусу завдання (виконано/не виконано)
    public Task toggleTaskCompletion(Long id) {
        Task existingTask = getTaskById(id); // Це перевірить доступ

        existingTask.setCompleted(!existingTask.isCompleted());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    // Отримання всіх завдань (тільки для адміністратора)
    public List<Task> getAllTasks(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Task> taskPage = taskRepository.findAll(pageable);
            return taskPage.getContent();
        } else {
            throw new RuntimeException("Access denied. Admin role required.");
        }
    }
}
