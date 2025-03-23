package org.example.mainpriject.dto.messages;

import org.example.mainpriject.enum_model.GroupType;
import org.example.mainpriject.model.GroupChat;

import java.time.Instant;
import java.util.List;

public class GroupChatResponseDto {

    private String id;
    private Long groupId;
    private String name;
    private Long creatorId;
    private String creatorName;
    private List<Long> participants;
    private List<Long> administrators;
    private Instant createdAt;
    private String description;
    private boolean isPrivate;
    private GroupType type;
    private String avatarUrl;
    private int participantCount;
    private int messageCount;
    private String lastMessagePreview;
    private Instant lastActivityTimestamp;

    public GroupChatResponseDto() {
    }

    public GroupChatResponseDto(String id, Long groupId, String name, Long creatorId, String creatorName, List<Long> participants, List<Long> administrators, Instant createdAt, String description, boolean isPrivate, String avatarUrl, int participantCount, int messageCount, String lastMessagePreview, Instant lastActivityTimestamp, GroupType type) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.participants = participants;
        this.administrators = administrators;
        this.createdAt = createdAt;
        this.description = description;
        this.isPrivate = isPrivate;
        this.avatarUrl = avatarUrl;
        this.participantCount = participantCount;
        this.messageCount = messageCount;
        this.lastMessagePreview = lastMessagePreview;
        this.lastActivityTimestamp = lastActivityTimestamp;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public List<Long> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<Long> administrators) {
        this.administrators = administrators;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getLastMessagePreview() {
        return lastMessagePreview;
    }

    public void setLastMessagePreview(String lastMessagePreview) {
        this.lastMessagePreview = lastMessagePreview;
    }

    public Instant getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(Instant lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
