package org.example.mainpriject.service.GroupAndChatService;

import org.example.mainpriject.dto.messages.ChatMessageDto;
import org.example.mainpriject.dto.messages.CreateMessageRequest;
import org.example.mainpriject.mapper.ChatMessageMapper;
import org.example.mainpriject.model.ChatMessage;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ChatMessageRepository;
import org.example.mainpriject.repository.GroupChatRepository;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final GroupChatRepository groupChatRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserService userService, UserRepository userRepository, GroupChatRepository groupChatRepository, ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.groupChatRepository = groupChatRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    public ChatMessageDto sendPrivateMessage(Long senderId, CreateMessageRequest request) {
        User user = userService.getCurrentUser();
        if (user.getId() != senderId) {
            throw new RuntimeException("Unauthorized sender");
        }

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessage message = chatMessageMapper.fromRequest(request);
        message.setSenderId(senderId);
        message.setReceiverId(receiver.getId());
        message.setTimestamp(Instant.now());
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Передаємо репозиторії як контекст

        return chatMessageMapper.toDto(savedMessage, userRepository, groupChatRepository);
    }

    public List<ChatMessageDto> getPrivateChatHistory(Long receiverId, Long senderId) {
        User user = userService.getCurrentUser();
        if (user.getId() != senderId) {
            throw new RuntimeException("Unauthorized sender");
        }
        List<ChatMessage> messages = chatMessageRepository.findPrivateMessages(receiverId, senderId);


        messages.stream()
                .filter(msg -> msg.getReceiverId().equals(senderId) && !msg.isRead())
                .forEach(msg -> {
                    msg.setRead(true);
                    chatMessageRepository.save(msg);
                });

        return messages.stream().map(msg -> {
            ChatMessageDto dto = chatMessageMapper.toDto(msg, userRepository, groupChatRepository);
            return dto;
        }).collect(Collectors.toList());
    }









}
