package org.example.mainpriject.service;

import org.example.mainpriject.model.GroupChat;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private UserService userService;

    public GroupChat createGroupChat(GroupChat groupChat) {

        if (groupChat == null) {
            throw new IllegalArgumentException("Створювач групи не може бути null");
        }

        User currentUser = userService.getCurrentUser();
        initializeGroupChat(groupChat, currentUser);

        return groupChatRepository.save(groupChat);

    }

    public List<GroupChat> searchByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Назва групи не може бути порожньою");
        }
        // Додав до репазиторію -> "Regex" щоб можна було реалізувати пошук "неповний" за частиною слова
        return groupChatRepository.findByNameRegexAndIsPrivateFalse(".*" + name + ".*");
    }

    public void initializeGroupChat(GroupChat groupChat, User creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Створювач групи не може бути null");
        }

        groupChat.setCreatorId(creator.getId());
        groupChat.setCreatedAt(Instant.now());

        List<Long> participants = new ArrayList<>();
        participants.add(creator.getId());
        groupChat.setParticipants(participants);

        if (groupChat.getName() == null || groupChat.getName().isEmpty()) {
            throw new IllegalArgumentException("Назва групи не може бути порожньою");
        }

    }

}
