package org.example.mainpriject.service;

import org.example.mainpriject.dto.CreateTaskDto;
import org.example.mainpriject.model.Task;
import org.example.mainpriject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15"
    );


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Configuration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    private final TaskService taskService;
    private final UserService userService;

    public TaskServiceTest(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @Test
    void shouldCreateTask() {
        // 1. Готуємо тестові дані
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("testUser");

        CreateTaskDto taskDto = new CreateTaskDto();
        taskDto.setTitle("Test Task");
        taskDto.setDescription("This is a test task");

        // 2. Мокаємо повернення поточного користувача
        when(userService.getCurrentUser()).thenReturn(mockUser);

        // 3. Викликаємо метод сервісу
        Task createdTask = taskService.createTask(taskDto);

        // 4. Перевіряємо результат
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("Test Task");
        assertThat(createdTask.getDescription()).isEqualTo("This is a test task");
        assertThat(createdTask.getOwner().getId()).isEqualTo(mockUser.getId());
    }


}