package org.example.mainpriject.repository;


import org.example.mainpriject.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    // Пошук повідомлень, де поточний користувач є відправником або отримувачем
    @Query("{ $and: [ { $or: [ { senderId: ?1 }, { receiverId: ?1 } ] }, { content: { $regex: ?0, $options: 'i' } } ] }")
    List<ChatMessage> findByContentAndUser(String content, Long userId);

    List<ChatMessage> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
