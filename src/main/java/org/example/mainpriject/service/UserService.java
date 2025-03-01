package org.example.mainpriject.service;

import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserDto userDto) {
        // Перевірка наявності користувача за email може бути додана
        User user = new User();
        user.setEmail(userDto.getEmail());
        // Шифруємо пароль (BCryptPasswordEncoder)
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        return userRepository.save(user);
    }

    // Метод для завантаження користувача (наприклад, для аутентифікації)
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

