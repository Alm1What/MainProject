package org.example.mainpriject.service.GroupAndChatService;

import org.example.mainpriject.dto.messages.CreateGroupChatDto;
import org.example.mainpriject.dto.messages.GroupChatRequestDto;
import org.example.mainpriject.dto.messages.GroupChatResponseDto;
import org.example.mainpriject.mapper.GroupChatMapper;
import org.example.mainpriject.model.GroupChat;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.GroupChatRepository;
import org.example.mainpriject.repository.UserRepository;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final UserRepository userRepository; // Додайте репозиторій користувачів
    private final GroupChatMapper groupChatMapper;
    private final SequenceGeneratorService sequenceGeneratorService; // Для генерації groupId
    private final UserService userService;

    @Autowired
    public GroupChatService(
            GroupChatRepository groupChatRepository,
            UserRepository userRepository,
            GroupChatMapper groupChatMapper,
            SequenceGeneratorService sequenceGeneratorService,
            UserService userService) {
        this.groupChatRepository = groupChatRepository;
        this.userRepository = userRepository;
        this.groupChatMapper = groupChatMapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.userService = userService;
    }

    public GroupChatResponseDto createGroupChat(CreateGroupChatDto groupChatDto, Long creatorId) {
        validateGroupChatCreation(groupChatDto, creatorId);

        GroupChat groupChat = groupChatMapper.createGroupChatDtoToGroupChat(groupChatDto);
        groupChat.setGroupId(sequenceGeneratorService.generateSequence(GroupChat.SEQUENCE_NAME));
        groupChat.setCreatorId(creatorId);


        List<Long> participants = new ArrayList<>();
        if (groupChatDto.getInitialParticipantIds() != null) {
            participants.addAll(groupChatDto.getInitialParticipantIds());
        }

        // Перевіряємо, чи автор вже в списку учасників, якщо ні - додаємо
        if (!participants.contains(creatorId)) {
            participants.add(creatorId);
        }

        groupChat.setParticipants(participants);
        groupChat.setAdministrators(Collections.singletonList(creatorId));


        // Зберігаємо в базу
        GroupChat savedGroupChat = groupChatRepository.save(groupChat);

        // Конвертуємо в DTO і повертаємо
        return groupChatMapper.groupChatToGroupChatResponseDto(savedGroupChat);
    }

    public void validateGroupChatCreation(CreateGroupChatDto dto, Long creatorId) {
        User user = userService.getCurrentUser();
        if (user.getId() != creatorId) {
            throw new RuntimeException("Unauthorized sender");
        }

        if (groupChatRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Group chat with name " + dto.getName() + " already exists");
        }

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Назва групи не може бути порожньою");
        }

        if (dto.getName().length() > 50) {
            throw new RuntimeException("Назва групи не може перевищувати 50 символів");
        }

        if (dto.getInitialParticipantIds() != null && !dto.getInitialParticipantIds().isEmpty()) {
            for (Long participantId : dto.getInitialParticipantIds()) {
                if (!userRepository.existsById(participantId)) {
                    throw new RuntimeException("Користувач з ID " + participantId + " не знайдений"); // NOt found потрібно додати
                }
            }
        }
    }

}
