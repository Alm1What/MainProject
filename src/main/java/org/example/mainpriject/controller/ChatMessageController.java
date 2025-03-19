package org.example.mainpriject.controller;

import org.example.mainpriject.dto.ChatMessageDTO;
import org.example.mainpriject.model.ChatMessage;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ChatMessageRepository;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestParam Long senderId,
                                   @RequestParam Long receiverId,
                                   @RequestParam String content) {
        return chatMessageService.sendMessage(senderId, receiverId, content);
    }

    @GetMapping("/history")
    public List<ChatMessageDTO> getChatHistory(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        List<ChatMessage> messages = chatMessageService.getChatHistory(senderId, receiverId);

        // Перетворення ChatMessage в ChatMessageDTO (з іменами користувачів)
        return messages.stream()
                .map(message -> {
                    User sender = userRepository.findById(message.getSenderId())
                            .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));
                    User receiver = userRepository.findById(message.getReceiverId())
                            .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));

                    return new ChatMessageDTO(
                            message.getId(),
                            sender.getName(),
                            receiver.getName(),
                            message.getContent(),
                            message.getTimestamp()
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchChatMessages(@RequestParam String query) {
        try {
            List<ChatMessage> messages = chatMessageService.getMessagesByContent(query);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public List<ChatMessage> getChatMessages() {
        return chatMessageService.getAllMessages();
    }


}
