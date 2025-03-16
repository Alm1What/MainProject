package org.example.mainpriject.controller;

import org.example.mainpriject.dto.CreateTaskDto;
import org.example.mainpriject.dto.TaskDto;
import org.example.mainpriject.model.Task;
import org.example.mainpriject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto createTaskDto) {
        Task task = taskService.createTask(createTaskDto);
        return ResponseEntity.ok(new TaskDto(task));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskDto>> getMyTasks() {
        List<Task> tasks = taskService.getUserTasks();
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(new TaskDto(task));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody CreateTaskDto taskDto) {
        Task task = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(new TaskDto(task));
    }

    @PatchMapping("/{id}/toggle-completion")
    public ResponseEntity<TaskDto> toggleTaskCompletion(@PathVariable Long id) {
        Task task = taskService.toggleTaskCompletion(id);
        return ResponseEntity.ok(new TaskDto(task));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Отримання всіх завдань (тільки для адміністратора)
    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Task> tasks = taskService.getAllTasks(page, size);
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }
}

