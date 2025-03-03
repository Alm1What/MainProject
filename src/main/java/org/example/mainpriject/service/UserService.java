package org.example.mainpriject.service;

import jakarta.annotation.PostConstruct;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.mapper.UserMapper;
import org.example.mainpriject.model.Role;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.RoleRepository;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper; // Додаємо mapper

    @PostConstruct
    public void init() {
        // Створюємо ролі, якщо їх не існує
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");
    }

    private void createRoleIfNotFound(String name) {
        if (!roleRepository.findByName(name).isPresent()) {
            Role role = new Role(name);
            roleRepository.save(role);
        }
    }

    public User register(UserDto userDto) {
        // Перевірка наявності користувача за email може бути додана
        User user = new User();
        user.setEmail(userDto.getEmail());
        // Шифруємо пароль (BCryptPasswordEncoder)
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());

        // Додаємо роль користувача за замовчуванням
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.addRole(userRole);

        return userRepository.save(user);
    }

    public User setAdminRole(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        user.addRole(adminRole);
        return userRepository.save(user);
    }

    // Метод для завантаження користувача (наприклад, для аутентифікації)
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Метод для отримання поточного автентифікованого користувача
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return loadUserByEmail(email);
    }

    // Новий метод з використанням MapStruct для оновлення профілю користувача
    @Transactional
    public UserDto updateUserProfile(Long userId, UserDto updatedUserDto) {
        // Знаходимо користувача за ID
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userId + " not found"));

        // Використовуємо MapStruct для оновлення користувача
        userMapper.updateUserFromDto(updatedUserDto, existingUser);

        // Зберігаємо оновленого користувача
        User savedUser = userRepository.save(existingUser);

        // Конвертуємо назад у DTO для повернення клієнту
        return userMapper.toDto(savedUser);
    }

    // Додатковий метод для зміни пароля (з перевіркою старого пароля)
    @Transactional
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Перевіряємо, чи правильний поточний пароль
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            // Шифруємо і встановлюємо новий пароль
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }

        return false; // Повертаємо false, якщо поточний пароль неправильний
    }

    public UserDto getCurrentUserDto() {
        User currentUser = getCurrentUser();
        return userMapper.toDto(currentUser);
    }
}