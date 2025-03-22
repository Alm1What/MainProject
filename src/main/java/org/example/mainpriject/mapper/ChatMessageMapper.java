package org.example.mainpriject.mapper;

import org.example.mainpriject.dto.messages.ChatMessageDto;
import org.example.mainpriject.dto.messages.CreateMessageRequest;
import org.example.mainpriject.model.ChatMessage;
import org.example.mainpriject.repository.GroupChatRepository;
import org.example.mainpriject.repository.UserRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {


    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "receiverName", ignore = true)
    @Mapping(target = "groupChatName", ignore = true)
    ChatMessageDto toDto(ChatMessage message);

    // Метод з контекстом
    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "receiverName", ignore = true)
    @Mapping(target = "groupChatName", ignore = true)
    ChatMessageDto toDto(ChatMessage message, @Context UserRepository userRepository, @Context GroupChatRepository groupChatRepository);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "read", constant = "false")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "senderId", ignore = true)
    @Mapping(target = "type", defaultExpression = "java(request.getType() != null ? request.getType() : ChatMessage.MessageType.TEXT)")
    ChatMessage fromRequest(CreateMessageRequest request);


    @AfterMapping
    default void enrichDto(@MappingTarget ChatMessageDto dto, ChatMessage message,
                           @Context UserRepository userRepository,
                           @Context GroupChatRepository groupChatRepository) {

        if (message.getSenderId() != null) {
            userRepository.findById(message.getSenderId())
                    .ifPresent(user -> dto.setSenderName(user.getName()));
        }

        if (message.getReceiverId() != null) {
            userRepository.findById(message.getReceiverId())
                    .ifPresent(user -> dto.setReceiverName(user.getName()));
        }

        if (message.getGroupChatId() != null) {
            groupChatRepository.findById(message.getGroupChatId())
                    .ifPresent(group -> dto.setGroupChatName(group.getName()));
        }
    }
}