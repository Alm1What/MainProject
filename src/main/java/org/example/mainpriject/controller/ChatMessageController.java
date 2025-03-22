package org.example.mainpriject.controller;

import org.example.mainpriject.dto.messages.ChatMessageDto;
import org.example.mainpriject.dto.messages.CreateMessageRequest;
import org.example.mainpriject.service.GroupAndChatService.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {



    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/users/{senderId}")
    public ChatMessageDto addChatMessage(@PathVariable Long senderId, @RequestBody CreateMessageRequest chatMessageRequest) {
        return chatMessageService.sendPrivateMessage(senderId, chatMessageRequest);
    }

    @GetMapping("/{senderId}")
    public List<ChatMessageDto> getAllChatMessages(@PathVariable Long senderId, @RequestParam Long receiverId) {
        return chatMessageService.getPrivateChatHistory(receiverId, senderId);
    }




}
