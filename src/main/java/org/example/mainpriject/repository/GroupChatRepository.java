package org.example.mainpriject.repository;


import org.example.mainpriject.model.GroupChat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GroupChatRepository extends MongoRepository<GroupChat, String> {


}
