package org.example.mainpriject.repository;

import org.example.mainpriject.model.GroupChatRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupChatRequestRepository extends MongoRepository<GroupChatRequest, String> {

}
