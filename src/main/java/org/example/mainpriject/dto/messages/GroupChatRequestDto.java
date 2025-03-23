package org.example.mainpriject.dto.messages;

import org.example.mainpriject.enum_model.RequestStatus;

import java.time.Instant;

public class GroupChatRequestDto {

    private String id;
    private Long groupId;
    private String groupName;
    private Long userId;
    private String userName;
    private Long creatorId;
    private String creatorName;
    private RequestStatus status;
    private Instant createdAt;


    public GroupChatRequestDto() {

    }

    public GroupChatRequestDto(String id, Long groupId, String groupName, Long userId, String userName, Long creatorId, String creatorName, RequestStatus status, Instant createdAt) {
        this.id = id;
        this.groupId = groupId;
        this.groupName = groupName;
        this.userId = userId;
        this.userName = userName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
