package org.example.mainpriject.service;

import org.example.mainpriject.exception.AccessDeniedException;
import org.example.mainpriject.exception.ResourceNotFoundException;
import org.example.mainpriject.model.ChatMessage;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ChatMessageRepository;
import org.example.mainpriject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ChatMessage sendMessage(Long senderId, Long receiverId, String content) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Відправник не знайдений"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Отримувач не знайдений")); // Краще напевно створити океру функцію для перевірки

        ChatMessage message = new ChatMessage();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content); // Створити для цього mapper
        message.setTimestamp(Instant.now());

        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(Long senderId, Long receiverId) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getId().equals(senderId) && !currentUser.getId().equals(receiverId)) {
            throw new AccessDeniedException("Ви не маєте доступу до цього чату");
        }

        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }


    public List<ChatMessage> getMessagesByContent(String query) {

        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Поле пошуку не може бути пустим");
        }

        User currentUser = userService.getCurrentUser();

        return chatMessageRepository.findByContentAndUser(query, currentUser.getId());

    }

    public List<ChatMessage> getAllMessages() {
        User currentUser = userService.getCurrentUser();

        return chatMessageRepository.findBySenderIdOrReceiverId(currentUser.getId(), currentUser.getId());
    }

}
