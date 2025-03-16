package org.example.mainpriject.service;

import jakarta.annotation.PostConstruct;
import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.exception.ValidationException;
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

    public User register(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Роль не знайдено"));
        user.addRole(userRole);

        return userRepository.save(user);
    }

    public User setAdminRole(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Роль адміністратора не знайдено"));

        user.addRole(adminRole);
        return userRepository.save(user);
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));
    }

    @Transactional
    public UserDto updateUserProfile(Long userId, UserDto updatedUserDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));

        userMapper.updateUserFromDto(updatedUserDto, existingUser);
        User savedUser = userRepository.save(existingUser);

        return userMapper.toDto(savedUser);
    }

    @Transactional
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Користувача не знайдено"));

        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }

        throw new ValidationException("Поточний пароль неправильний");
    }

    // Метод для отримання поточного автентифікованого користувача
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return loadUserByEmail(email);
    }
    public UserDto getCurrentUserDto() {
        User currentUser = getCurrentUser();
        return userMapper.toDto(currentUser);
    }
}