package org.example.mainpriject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group_chat_request")
public class GroupChatRequest {

    @Id
    private String id;
    private Long groupId;
    private Long userId;
    private Long creatorId;
    private RequestStatus status;

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }

    public GroupChatRequest() {
    }

    public GroupChatRequest(String id, Long groupId, Long userId, Long creatorId, RequestStatus status) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.creatorId = creatorId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
