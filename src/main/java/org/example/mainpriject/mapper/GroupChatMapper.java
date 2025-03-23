package org.example.mainpriject.mapper;


import org.example.mainpriject.dto.messages.CreateGroupChatDto;
import org.example.mainpriject.dto.messages.GroupChatResponseDto;
import org.example.mainpriject.model.GroupChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupChatMapper {

    // Конвертація з DTO до сутності при створенні
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "creatorId", ignore = true)  // creatorId буде встановлено в сервісі
    @Mapping(target = "participants", ignore = true)  // participants буде встановлено в сервісі
    @Mapping(target = "administrators", ignore = true)  // administrators буде встановлено в сервісі
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")  // Встановлюємо поточний час
    @Mapping(target = "messageCount", constant = "0")  // Початкова кількість повідомлень
    @Mapping(target = "lastMessagePreview", constant = "")  // Порожнє превʼю останнього повідомлення
    @Mapping(target = "lastActivityTimestamp", expression = "java(java.time.Instant.now())")  // Останню активність встановлюємо як поточний час
    @Mapping(target= "type", ignore = true)
    GroupChat createGroupChatDtoToGroupChat(CreateGroupChatDto dto);

    // Конвертація з сутності до DTO відповіді
    GroupChatResponseDto groupChatToGroupChatResponseDto(GroupChat groupChat);

    // Оновлення існуючої групи з DTO
    void updateGroupChatFromDto(CreateGroupChatDto dto, @MappingTarget GroupChat groupChat);

}
