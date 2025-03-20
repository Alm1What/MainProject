package org.example.mainpriject.repository;


import org.example.mainpriject.model.GroupChat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupChatRepository extends MongoRepository<GroupChat, String> {
    List<GroupChat> findByNameRegexAndIsPrivateFalse(String name);
}
