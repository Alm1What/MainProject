package org.example.mainpriject.service;


import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtilityService {

    private final UserRepository userRepository;

    @Autowired
    public UserUtilityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserIdFromUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return user.getId();
    }
}

