package org.example.mainpriject.repository;

import org.example.mainpriject.model.GroupChatRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupChatRequestRepository extends MongoRepository<GroupChatRequest, String> {
    List<GroupChatRequest> findByCreatorId(Long creatorId);
}
