package org.example.mainpriject.repository;


import org.example.mainpriject.dto.messages.ChatMessageDto;
import org.example.mainpriject.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    @Query("{'$or': [{'senderId': ?0, 'receiverId': ?1}, {'senderId': ?1, 'receiverId': ?0}], 'isDeleted': false}")
    List<ChatMessage> findPrivateMessages(Long userId1, Long userId2);

}
